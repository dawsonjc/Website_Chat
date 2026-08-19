<%@ page import="com.brewery.web.model.User" %>
<%
    User user = (User) request.getSession().getAttribute("current_user");
    String displayName = user == null ? "John Doe" : user.getUsername();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= title %></title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        "desert-storm": "#f8f9f7",
                        "boulder": "#767676",
                        "gray-nickel": "#bcbcbb",
                        "jakarta": "#362c5f",
                        "darker-jakarta": "#261f42",
                        "lighter-scarlet-gum": "#a355d2",
                        "light-scarlet-gum": "#71299c",
                        "scarlet-gum": "#44195e",
                        "blaze-orange": "#fc6601",
                        "sandy-brown": "#ef955d",
                        "hawaiian-tan": "#924518"
                    }
                }
            }
        };
    </script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <script src="<%= request.getContextPath() %>/static/js/main.js"></script>
</head>
<body class="m-0 min-h-screen bg-[#f2f3f3] font-sans text-[#30343b]">
<header class="fixed inset-x-0 top-0 z-50 flex h-10 bg-[#1f2229] text-[#d8dadd]">
    <a href="<%= request.getContextPath() %>/" class="flex w-16 shrink-0 items-center justify-center bg-[#20b49f] text-lg font-light text-white sm:w-40 sm:justify-start sm:px-9 sm:text-xl">
        <span class="sm:hidden">JA</span><span class="hidden whitespace-nowrap sm:inline">Joli Admin</span>
    </a>
    <div class="flex min-w-0 flex-1 items-center justify-between px-3.5">
        <div class="flex items-center gap-2">
            <button type="button" class="grid h-7 w-7 place-items-center hover:bg-white/5 hover:text-white" aria-label="Toggle navigation"><i class="fa-solid fa-bars-staggered"></i></button>
            <label class="relative hidden w-24 sm:block">
                <i class="fa-solid fa-magnifying-glass absolute left-2 top-1/2 -translate-y-1/2 text-xs text-[#9da1a8]"></i>
                <input type="search" class="h-6 w-full rounded-sm border border-[#292d35] bg-[#181b22] py-0 pl-7 pr-2 text-xs text-[#dfe1e4] outline-none" placeholder="Search..." aria-label="Search">
            </label>
        </div>
        <div class="flex items-center gap-2">
            <button type="button" class="relative grid h-7 w-7 place-items-center hover:bg-white/5 hover:text-white" aria-label="Tasks"><i class="fa-solid fa-list"></i><span class="absolute -right-1 -top-1 min-w-4 scale-75 rounded-sm bg-[#20b49f] px-1 text-center text-xs leading-4 text-white">3</span></button>
            <button type="button" class="relative grid h-7 w-7 place-items-center hover:bg-white/5 hover:text-white" aria-label="Notifications"><i class="fa-solid fa-bell"></i><span class="absolute -right-1 -top-1 min-w-4 scale-75 rounded-sm bg-[#e45151] px-1 text-center text-xs leading-4 text-white">4</span></button>
            <a id="logout" href="#" class="grid h-7 w-7 place-items-center hover:bg-white/5 hover:text-white" aria-label="Log out"><i class="fa-solid fa-right-from-bracket"></i></a>
        </div>
    </div>
</header>
