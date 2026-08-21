# Header notifications backend

The notification backend stores each notification in the existing Cassandra
projections and pushes changes to the notification recipient over SSE.

## Backend API

All routes use the `current_user` session attribute established by `AuthHook`.
The browser never supplies a user ID.

| Method | Route | Purpose |
| --- | --- | --- |
| `GET` | `/api/events?limit=25` | Current visible notifications and unread count |
| `GET` | `/api/events?limit=25&type=new_message` | Same response filtered by `EventType` code or enum name |
| `GET` | `/api/events/count` | Unread count only |
| `GET` | `/api/events/stream?limit=25` | Live SSE connection; starts with a snapshot |
| `PATCH` | `/api/events/{eventId}/read` | Mark one notification read |
| `PATCH` | `/api/events/read-all` | Mark every notification read |
| `PATCH` | `/api/events/{eventId}/archive` | Archive and mark one notification read |

The snapshot shape is:

```json
{
  "notifications": [
    {
      "userId": "...",
      "eventId": "...",
      "createDate": "2026-08-20T20:00:00Z",
      "eventType": "NEW_MESSAGE",
      "actorId": "...",
      "actorName": "Dawson",
      "title": "New message",
      "message": "You received a message",
      "actionUrl": "/conversations?conversationId=...",
      "metadata": { "conversationId": "..." },
      "isRead": false,
      "isArchived": false
    }
  ],
  "unreadCount": 1
}
```

## Creating notifications in backend code

Inject `NotificationService` into the service that completes the underlying
action. `create` writes `event`, `events_by_type`, and `unread_events`, then pushes
the new notification and updated count to the recipient.

```java
private final NotificationService notifications;

public SomeService(NotificationService notifications) {
    this.notifications = notifications;
}

public void notifyAboutMessage(User recipient, User sender, Message message) {
    notifications.create(new CreateEventRequest(
            recipient.getUserId(),
            EventType.NEW_MESSAGE,
            sender.getUserId(),
            sender.getUsername(),
            "New message",
            sender.getUsername() + " sent you a message",
            "/conversations?conversationId=" + message.getConversationId(),
            Map.of(
                    "conversationId", message.getConversationId().toString(),
                    "messageId", message.getMessageId().toString()
            ),
            null
    ));
}
```

Do this only after the message/friend request/etc. has been saved successfully.
There is deliberately no public notification-creation endpoint: application code,
not a browser, decides who receives a notification.

## Connecting `header.jsp`

No frontend files were changed. The header can keep one `EventSource` for the page:

```javascript
const notificationStream = new EventSource("/api/events/stream?limit=25");

notificationStream.addEventListener("notifications_snapshot", event => {
  const snapshot = JSON.parse(event.data);
  renderNotificationList(snapshot.notifications);
  renderNotificationCount(snapshot.unreadCount);
});

notificationStream.addEventListener("notification_created", event => {
  const notification = JSON.parse(event.data);
  prependNotification(notification);
});

notificationStream.addEventListener("notification_updated", event => {
  const notification = JSON.parse(event.data);
  updateOrRemoveNotification(notification);
});

notificationStream.addEventListener("notification_count", event => {
  const { unreadCount } = JSON.parse(event.data);
  renderNotificationCount(unreadCount);
});
```

The named SSE messages are:

- `connected`: connection metadata; normally no UI action is needed.
- `notifications_snapshot`: complete initial list plus unread count.
- `notification_created`: one new `EventDTO` to prepend.
- `notification_updated`: one read/archived `EventDTO` to update or remove.
- `notification_count`: `{ "unreadCount": number }`.

To mark a notification read from the header:

```javascript
await fetch(`/api/events/${eventId}/read`, { method: "PATCH" });
```

`EventSource` automatically includes same-origin session cookies and reconnects
after a dropped connection. Call `notificationStream.close()` only when the page
or component is permanently disposing the stream.

## Deployment behavior

The open HTTP connections are in memory, while notification data remains in
Cassandra. A reconnect always receives a fresh Cassandra snapshot, so losing a
connection does not lose stored notifications. If the application is later run on
multiple backend instances, add Redis/Kafka (or sticky sessions) so an event created
on one instance can reach SSE connections held by another instance.
