<%@ page import="com.brewery.web.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.brewery.web.model.Role" %>
<%
    User admin = (User) request.getSession().getAttribute("current_user");
    System.err.println("Accessed");
    if(!admin.getRoles().contains("Admin")) {
        response.sendRedirect("/");
        return;
    }
%>

<%! private final String title = "Admin - Panel"; %>
<%@ include file="../header.jsp"%>
<main>
    <div class="overflow-x-auto max-w-5xl mx-auto mt-6">
        <table class="min-w-full table-auto text-sm border border-gray-200 rounded shadow">
            <thead class="bg-gray-100 text-gray-700 uppercase text-xs">
            <tr>
                <th colspan="9">Unverified Users</th>
            </tr>
            <tr>
                <th class="px-3 py-2 text-left">Username</th>
                <th class="px-3 py-2 text-left">Email</th>
                <th class="px-3 py-2 text-left">Full Name</th>
                <th class="px-3 py-2 text-left">Language</th>
                <th class="px-3 py-2 text-left">Timezone</th>
                <th class="px-3 py-2 text-left">Last Login</th>
                <th class="px-3 py-2 text-left">2FA</th>
                <th class="px-3 py-2 text-left">Status</th>
                <th class="px-3 py-2 text-left">Actions</th>
            </tr>
            </thead>
            <tbody class="text-gray-800">
            <%
                List<User> unverifiedUsers = (List<User>) request.getAttribute("unverified_users");

                for(User unverifiedUser : unverifiedUsers) {
            %>
            <tr class="bg-white hover:bg-gray-50">
                <td class="px-3 py-2"><%= unverifiedUser.getUsername() %></td>
                <td class="px-3 py-2"><%= unverifiedUser.getEmail() %></td>
                <td class="px-3 py-2"><%= unverifiedUser.getFullName() %></td>
                <td class="px-3 py-2"><%= unverifiedUser.getLanguagePreference() %></td>
                <td class="px-3 py-2"><%= unverifiedUser.getTimezone() %></td>
                <td class="px-3 py-2"><%= unverifiedUser.getLastLoginDate() %></td>
                <td class="px-3 py-2"><%= unverifiedUser.isTwoFactorAuthentication() %></td>
                <td class="px-3 py-2 text-red-600 font-semibold">Unverified</td>
                <td class="px-3 py-2 space-x-2">
                    <form style="display: none;">
                        <input type="hidden" name="userId" value="<%= unverifiedUser.getUserId() %>" />
                        <input type="hidden" name="username" value="<%= unverifiedUser.getUsername() %>" />
                        <input type="hidden" name="email" value="<%= unverifiedUser.getEmail() %>" />
                        <input type="hidden" name="fullName" value="<%= unverifiedUser.getFullName() %>" />
                        <input type="hidden" name="languagePreference" value="<%= unverifiedUser.getLanguagePreference() %>" />
                        <input type="hidden" name="timezone" value="<%= unverifiedUser.getTimezone() %>" />
                        <input type="hidden" name="lastLoginDate" value="<%= unverifiedUser.getLastLoginDate() %>" />
                        <input type="hidden" name="twoFactorAuthentication" value="<%= unverifiedUser.isTwoFactorAuthentication() %>" />
                    </form>
                    <button class="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded text-xs verify-user">Verify</button>
                    <button class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded text-xs delete-user">Delete</button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
    <div class="overflow-x-auto max-w-5xl mx-auto mt-6">
        <table class="min-w-full table-auto text-sm border border-gray-200 rounded shadow">
            <thead class="bg-gray-100 text-gray-700 uppercase text-xs">
            <tr>
                <th colspan="3">Roles</th>
            </tr>
            <tr>
                <th class="px-3 py-2 text-left">Role Name</th>
                <th class="px-3 py-2 text-left">Actions</th>
                <th class="px-3 py-2 text-left">
                    <button class="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded text-xs add-role">Add Role</button>
                </th>
            </tr>
            </thead>
            <tbody class="text-gray-800">
            <%
                List<Role> roles = (List<Role>) request.getAttribute("all_roles");

                for(Role role : roles) {
            %>
            <tr class="bg-white hover:bg-gray-50">
                <td class="px-3 py-2"><%= role.getRoleName() %></td>
                <td colspan="2" class="px-3 py-2 space-x-2">
                    <form style="display: none;">
                        <input type="hidden" name="roleId" value="<%= role.getRoleId() %>" />
                    </form>
                    <button class="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded text-xs edit-role">Edit</button>
                    <button class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded text-xs delete-role">Delete</button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</main>

<%@ include file="../footer.jsp"%>