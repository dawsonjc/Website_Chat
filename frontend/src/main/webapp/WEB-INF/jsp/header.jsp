<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.brewery.web.model.User" %>
<%
    User user = (User) request.getSession().getAttribute("current_user");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= title %></title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/static/js/main.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <script type="text/javascript">
        {
            const colors = new Object();
            colors["black"] = "#010101";
            colors["desert-storm"] = "#f8f9f7";
            colors["boulder"] = "#767676";
            colors["gray-nickel"] = "#bcbcbb";
            colors["jakarta"] = "#362c5f";
            colors["darker-jakarta"] = "#261f42";
            colors["lighter-scarlet-gum"] = "#a355d2";
            colors["light-scarlet-gum"] = "#71299c";
            colors["scarlet-gum"] = "#44195e";
            colors["blaze-orange"] = "#fc6601";
            colors["sandy-brown"] = "#ef955d";
            colors["hawaiian-tan"] = "#924518";

            const extend = new Object();
            extend["colors"] = colors;

            const theme = new Object();
            theme["extend"] = extend;

            const config = new Object();
            config["theme"] = theme;

            tailwind["config"] = config;
        }
    </script>
</head>
<body>
<div class="flex flex-col h-screen">
    <header>
        <nav class="bg-gradient-to-t from-jakarta to-scarlet-gum">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div class="flex items-center justify-between h-16">
                    <!-- Logo -->
                    <div class="flex-shrink-0">
                        <img class="object-scale-down h-14 w-full" src="<%= request.getContextPath() %>/static/assets/images/stolen_logo.jpg" alt="stolen Logo">
                    </div>

                    <!-- Navigation Links -->
                    <div class="hidden md:block">
                        <ul class="ml-4 flex space-x-4">
                            <li><a href="#" class="text-gray-300 hover:text-white">Home</a></li>
                            <li><a href="#" class="text-gray-300 hover:text-white">About</a></li>
                            <li><a href="#" class="text-gray-300 hover:text-white">Services</a></li>
                            <li><a href="#" class="text-gray-300 hover:text-white">Portfolio</a></li>
                            <li><a href="#" class="text-gray-300 hover:text-white">Contact</a></li>
                        </ul>
                    </div>

                    <!-- Mobile Menu Button (hidden on larger screens) -->
                    <div class="md:hidden">
                        <button class="text-white hover:text-gray-300 focus:outline-none">
                            <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16"></path>
                            </svg>
                        </button>
                    </div>
                </div>
            </div>

            <!-- Mobile Menu (hidden on larger screens) -->
            <div class="md:hidden">
                <div class="px-2 pt-2 pb-3 space-y-1 sm:px-3">
                    <a href="#" class="text-gray-300 hover:text-white">Home</a>
                    <a href="#" class="text-gray-300 hover:text-white">About</a>
                    <a href="#" class="text-gray-300 hover:text-white">Services</a>
                    <a href="#" class="text-gray-300 hover:text-white">Portfolio</a>
                    <a href="#" class="text-gray-300 hover:text-white">Contact</a>
                </div>
            </div>
        </nav>
    </header>