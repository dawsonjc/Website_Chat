<%@ page contentType="application/json" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.databind.node.ObjectNode" %><%
  ObjectMapper mapper = new ObjectMapper();

  ObjectNode node = mapper.createObjectNode();

  node.put("fuck", 1);
%>
<%= node.toString() %>