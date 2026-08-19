<%@ page import="com.brewery.web.dto.ConversationDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.brewery.web.model.message.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.Instant" %>

<%! private final String title = "Chat"; %>
<%@ include file="header.jsp"%>

<main class="bg-gray-100 flex h-lvh overflow-y-auto overflow-x-clip">
  <!-- Sidebar -->
  <div class="bg-jakarta text-white w-1/6">
    <h1 class="text-2xl font-semibold p-4">Channels</h1>
    <ul class="overflow-y-auto">
      <!-- List of Channels -->
      <%
        ArrayList<ConversationDTO> channels = (ArrayList<ConversationDTO>) request.getAttribute("Channels");

        if(channels != null) {
          for(ConversationDTO channel : channels) {
            UUID conversationId = channel.conversationId();

            String name = channel.name();
            String[] names = name.split("\\|");
            if(names.length == 2) {
              String newName = "";
              for(String n : names) {
                if(!n.equals(user.getUsername())) {
                  newName = n;
                }
              }
              name = newName;
            } else if(!name.contains("|") && names.length > 2) {
            } else {
              name = name.replaceAll("\\|", ",");
            }

            String channelLink = request.getContextPath() + "/conversations?conversationId=" + conversationId;
      %>
      <li class="mb-2">
        <a href="<%= channelLink %>" class="hover:text-blue-300 px-4 py-2 block"><%= name %></a>
      </li>
      <%
          }
        }
      %>
    </ul>
  </div>

  <!-- Chat Container -->
  <div class="flex flex-1 flex-col">
    <!-- Chat Messages Container -->
    <div class="bg-jakarta p-4 flex-1 border-l overflow-y-auto" id="chat-messages">
      <%
        List<Message> messages = (ArrayList<Message>) request.getAttribute("Messages");

        if(messages != null) {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")
                  .withLocale(java.util.Locale.US)
                  .withZone(java.time.ZoneId.of("UTC"));

          // TODO: Think about the logic for spacing on messages sent from the same user, needs a flag
          // and a few other things
          for(int i = messages.size()-1; i >= 0; i--) {
            Message message = messages.get(i);
            String messageDate = formatter.format(message.getUpdateDate());
      %>
      <div class="my-4 border border-transparent rounded-lg hover:border hover:border-black hover:rounded-lg hover:bg-darker-jakarta hover:transition hover:duration-75 relative user-message-box">
        <form style="display: none">
          <input type="hidden" name="user-id" value="<%= message.getFromUserId() %>">
          <input type="hidden" name="message-id" value="<%= message.getMessageId() %>">
          <input type="hidden" name="message-date" value="<%= messageDate %>">
        </form>
        <div class="p-2 text-desert-storm">
          <%= message.getFromUsername() %>: <span class="message-date"><%= messageDate %></span>
          <!-- Trash icon in top right -->


          <% if(user.getUserId().equals(message.getFromUserId()) || user.getRoles().contains("Admin")) { %>
          <div class="absolute top-2 right-2 cursor-pointer delete-message">
            <i class="fas fa-trash-alt text-desert-storm hover:text-red-500"></i>
          </div>
          <% } %>
        </div>
        <div class="p-2 rounded-lg text-desert-storm break-all"><%= message.getContent() %></div>
      </div>
      <%
          }
        }
      %>
    </div>
    <!-- Input Message and Submit Button -->
    <div class="bg-jakarta border-l p-4">
      <div class="flex items-center">
        <form id="send-message" style="display: contents;" method="post" action="<%= request.getContextPath() %>/message">
          <input type="hidden" name="user-id" value="<%= ((User) request.getSession().getAttribute("current_user")).getUserId() %>" />
          <input type="hidden" name="to-user-id" value="" />
          <input type="hidden" name="to-username" value="" />

          <input id="message-input" autocomplete="off" name="message-content" type="text" class="w-full p-2 rounded-l-md border border- outline-none bg-darker-jakarta text-desert-storm" placeholder="Type your message...">
          <button type="submit"  class="bg-light-scarlet-gum hover:bg-lighter-scarlet-gum text-white p-2 rounded-r-md">Send</button>
        </form>
      </div>
    </div>
  </div>

</main>
<%@ include file="footer.jsp" %>