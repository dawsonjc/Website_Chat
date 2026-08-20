
<%
    String title = "Register";
%>
<%@ include file="../header.jsp"%>

<main class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8">
        <div>
            <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
                Register
            </h2>
        </div>

        <form method="POST" id="register-form" >


            <div class="rounded-md shadow-sm -space-y-px">
                <div>
                    <label for="first-name" class="block text-sm font-medium text-gray-700 mb-1">First Name</label>
                    <input id="first-name" name="firstName" type="text"
                            class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                            placeholder="Jeremiah" required="required"/>
                </div>
            </div>

            <div class="rounded-md shadow-sm -space-y-px">
                <div>
                    <label for="last-name" class="block text-sm font-medium text-gray-700 mb-1">Last Name</label>
                    <input id="last-name" name="lastName" type="text"
                            class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                            placeholder="Corbyn" required="required"/>
                </div>
            </div>
            <div class="rounded-md shadow-sm -space-y-px">
                <div>
                    <label for="username" class="block text-sm font-medium text-gray-700 mb-1">Username</label>
                    <input id="username" name="username" type="text"
                            class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                            placeholder="" required="required"/>
                </div>
            </div>

            <!-- Email input -->
            <div class="rounded-md shadow-sm -space-y-px">
                <div>
                    <label for="email" class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                    <input id="email" name="email" type="text"
                            class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                            placeholder="example@email.com" required="required"/>
                </div>
            </div>

            <div class="rounded-md shadow-sm -space-y-px">
                <div>
                    <label for="password" class="block text-sm font-medium text-gray-700 mb-1">Password</label>
                    <input id="password" name="password" type="password"
                            class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                            placeholder="Password" required="required" />
                </div>
            </div>

            <div class="flex items-center justify-between">
                <!-- Forgot Password link -->
                <div class="text-sm">
                    <a href="<%= request.getContextPath() %>/account/forgot-password" class="font-medium text-indigo-600 hover:text-indigo-500">
                        Forgot your password?
                    </a>
                </div>
                <div class="text-sm">
                    <a href="<%= request.getContextPath() %>/account/register" class="font-medium text-indigo-600 hover:text-indigo-500">
                        Register
                    </a>
                </div>
            </div>

            <!-- Register button -->
            <div>
                <button type="submit"
                        class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                    Register
                </button>
            </div>
        </form>
    </div>
</main>

<%@ include file="../footer.jsp"%>