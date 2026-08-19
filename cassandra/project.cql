CREATE KEYSPACE IF NOT EXISTS mykeyspacename WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1 };

CREATE TABLE IF NOT EXISTS mykeyspacename.user_credential (
    id UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    user_id UUID,
    special_key TEXT,

    PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS mykeyspacename.user_table (
    userid UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    username TEXT,
    email TEXT,
    password TEXT,
    first_name TEXT,
    last_name TEXT,
    full_name TEXT,
    language_preference TEXT,
    timezone TEXT,
    last_login_date TIMESTAMP,
    account_verification_status TEXT,
    two_factor_authentication BOOLEAN,
    friends_connections SET<UUID>,
    blocked_users SET<UUID>,
    auth_api_token UUID,

    PRIMARY KEY ( userid )
);

CREATE TABLE IF NOT EXISTS mykeyspacename.roles (
    roleid UUID PRIMARY KEY,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    role_name TEXT,
    description TEXT
);

CREATE TABLE IF NOT EXISTS mykeyspacename.user_role (
     create_date TIMESTAMP,
     update_date TIMESTAMP,
     status TEXT,
     userid UUID,
     roleid UUID,
     role_name TEXT,
     username TEXT,

     PRIMARY KEY ( userid, roleid )
);

CREATE TABLE IF NOT EXISTS mykeyspacename.messages (
    messageid UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    conversation_id UUID,
    from_user_id UUID,
    from_username TEXT,
    to_user_id UUID,
    to_username TEXT,
    content TEXT,

    PRIMARY KEY ( messageid, create_date, conversation_id )
) WITH CLUSTERING ORDER BY ( create_date DESC, conversation_id DESC );

CREATE TABLE IF NOT EXISTS mykeyspacename.conversations (
    conversationid UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    name TEXT,
    users SET<UUID>,

    PRIMARY KEY ( conversationid )
);

CREATE TABLE  IF NOT EXISTS mykeyspacename.event (
    user_id uuid,
    event_id timeuuid,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status text,
    event_type text,
    actor_id uuid,
    actor_name text,
    title text,
    message text,
    action_url text,
    metadata map<text, text>,
    is_read boolean,
    is_archived boolean,
    expires_at timestamp,
    PRIMARY KEY (user_id, event_id)
) WITH CLUSTERING ORDER BY (event_id DESC);

CREATE TABLE  IF NOT EXISTS mykeyspacename.events_by_type (
    user_id uuid,
    event_type text,
    event_id timeuuid,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status text,
    actor_id uuid,
    actor_name text,
    title text,
    message text,
    action_url text,
    metadata map<text, text>,
    is_read boolean,
    is_archived boolean,
    expires_at timestamp,
    PRIMARY KEY ((user_id, event_type), event_id)
) WITH CLUSTERING ORDER BY (event_id DESC)
   AND comment = 'Events partitioned by user and type for filtered queries'
   AND gc_grace_seconds = 86400
   AND compaction = {
    'class': 'TimeWindowCompactionStrategy',
    'compaction_window_size': 1,
    'compaction_window_unit': 'DAYS'
    };

CREATE TABLE IF NOT EXISTS  mykeyspacename.unread_events (
       user_id uuid,
       event_id timeuuid,
       create_date TIMESTAMP,
       update_date TIMESTAMP,
       status text,
       event_type text,
       actor_id uuid,
       actor_name text,
       title text,
       message text,
       action_url text,
       metadata map<text, text>,
       expires_at timestamp,
       PRIMARY KEY (user_id, event_id)
) WITH CLUSTERING ORDER BY (event_id DESC)
   AND comment = 'Unread events only - deleted when marked as read'
   AND gc_grace_seconds = 86400
   AND compaction = {
            'class': 'TimeWindowCompactionStrategy',
            'compaction_window_size': 1,
            'compaction_window_unit': 'DAYS'
            };

CREATE INDEX IF NOT EXISTS ON mykeyspacename.events (is_read);
CREATE INDEX IF NOT EXISTS ON mykeyspacename.events (event_type);

CREATE INDEX IF NOT EXISTS ON mykeyspacename.conversations (users);

CREATE MATERIALIZED VIEW IF NOT EXISTS mykeyspacename.messages_by_conversation_id AS
    SELECT * FROM mykeyspacename.messages WHERE conversation_id IS NOT NULL AND create_date IS NOT NULL AND messageid IS NOT NULL
    PRIMARY KEY (conversation_id, create_date, messageid) WITH CLUSTERING ORDER BY (create_date DESC, messageid ASC);

INSERT INTO mykeyspacename.conversations(conversationid, create_date, update_date, status, name) VALUES(00000000-0000-0000-0000-000000000001, totimestamp(now()), totimestamp(now()), 'Active', 'Global Chat');
INSERT INTO mykeyspacename.roles(roleid, create_date, update_date, status, role_name, description) VALUES (00000000-0000-0000-0000-000000000101, totimestamp(now()), totimestamp(now()), 'Active', 'Admin', 'Admin');
INSERT INTO mykeyspacename.roles(roleid, create_date, update_date, status, role_name, description) VALUES (00000000-0000-0000-0000-000000000102, totimestamp(now()), totimestamp(now()), 'Active', 'Moderator', 'Moderator');
INSERT INTO mykeyspacename.roles(roleid, create_date, update_date, status, role_name, description) VALUES (00000000-0000-0000-0000-000000000103, totimestamp(now()), totimestamp(now()), 'Active', 'User', 'User');

-- INSERT INTO mykeyspacename.user_role(create_date, update_date, status, userid, roleid, role_name) VALUES(totimestamp(now()), totimestamp(now()), 'Active', ..., ..., 'Admin');
