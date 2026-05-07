<%
    String title = "Forgot Password";
%>
<%@ include file="../header.jsp" %>

<form:form method="POST" modelAttribute="user" action="/forgot-password" >
    <!-- Username | Email input -->
    <div class="rounded-md shadow-sm -space-y-px">
        <div>
            <label for="username" class="sr-only">Username</label>
            <form:input path="username" id="username" type="email"
                    class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                    placeholder="username" required="required" />
        </div>
    </div>

    <div class="flex items-center justify-between">
        <!-- Forgot Password link -->
        <div class="text-sm">
            <a href="<%= request.getContextPath() %>/account/login" class="font-medium text-indigo-600 hover:text-indigo-500">
                Back to Login
            </a>
        </div>
    </div>

    <!-- Sign in button -->
    <div>
        <button type="submit"
                class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
            Submit
        </button>
    </div>
</form:form>