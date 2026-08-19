<%! private final String title = "Dashboard"; %>
<%@ include file="header.jsp" %>

<aside class="fixed bottom-0 left-0 top-10 z-40 w-16 overflow-y-auto bg-[#1d2028] text-[#959aa5] sm:w-40" aria-label="Admin navigation">
    <div class="border-b border-[#292d35] bg-[#171a21] px-1.5 py-3 text-center sm:min-h-32 sm:px-2.5 sm:pb-2.5">
        <div class="flex items-center justify-center gap-2.5">
            <a href="#" class="hidden h-6 w-6 place-items-center rounded-md border border-[#cbd0d5] text-xs text-white sm:grid" aria-label="Profile information"><i class="fa-solid fa-info"></i></a>
            <img class="h-12 w-12 rounded-lg border-2 border-[#f0f0f0] bg-white object-cover p-1 sm:h-20 sm:w-20 sm:rounded-xl" src="<%= request.getContextPath() %>/static/assets/images/stolen_logo.jpg" alt="<%= displayName %> profile picture">
            <a href="#" class="hidden h-6 w-6 place-items-center rounded-md border border-[#cbd0d5] text-xs text-white sm:grid" aria-label="Messages"><i class="fa-solid fa-envelope"></i></a>
        </div>
        <div class="mt-1.5 hidden text-xs text-white sm:block"><%= displayName %></div>
        <div class="mt-0.5 hidden text-xs text-[#9095a0] sm:block">Web Developer/Designer</div>
    </div>

    <div class="hidden px-2 pb-2 pt-4 text-xs text-[#676d78] sm:block">Navigation</div>
    <nav><ul class="m-0 list-none p-0">
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-y border-black/10 bg-[#20b49f] px-3 text-xs text-white sm:justify-start"><i class="w-3 text-center fa-solid fa-desktop"></i><span class="hidden sm:inline">Dashboard</span></a></li>
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-b border-black/10 px-3 text-xs hover:bg-[#252932] hover:text-white sm:justify-start"><i class="w-3 text-center fa-regular fa-copy"></i><span class="hidden sm:inline">Pages</span><i class="fa-regular fa-square-plus ml-auto hidden scale-75 sm:inline"></i></a></li>
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-b border-black/10 px-3 text-xs hover:bg-[#252932] hover:text-white sm:justify-start"><i class="w-3 text-center fa-regular fa-file-lines"></i><span class="hidden sm:inline">Layouts</span><i class="fa-regular fa-square-plus ml-auto hidden scale-75 sm:inline"></i></a></li>
    </ul></nav>

    <div class="hidden px-2 pb-2 pt-4 text-xs text-[#676d78] sm:block">Components</div>
    <nav><ul class="m-0 list-none p-0">
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-b border-black/10 px-3 text-xs hover:bg-[#252932] hover:text-white sm:justify-start"><i class="w-3 text-center fa-solid fa-gears"></i><span class="hidden sm:inline">UI Kits</span><i class="fa-regular fa-square-plus ml-auto hidden scale-75 sm:inline"></i></a></li>
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-b border-black/10 px-3 text-xs hover:bg-[#252932] hover:text-white sm:justify-start"><i class="w-3 text-center fa-solid fa-pen"></i><span class="hidden sm:inline">Forms</span><i class="fa-regular fa-square-plus ml-auto hidden scale-75 sm:inline"></i></a></li>
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-b border-black/10 px-3 text-xs hover:bg-[#252932] hover:text-white sm:justify-start"><i class="w-3 text-center fa-solid fa-table-cells"></i><span class="hidden sm:inline">Tables</span><i class="fa-regular fa-square-plus ml-auto hidden scale-75 sm:inline"></i></a></li>
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-b border-black/10 px-3 text-xs hover:bg-[#252932] hover:text-white sm:justify-start"><i class="w-3 text-center fa-solid fa-chart-column"></i><span class="hidden sm:inline">Charts</span><i class="fa-regular fa-square-plus ml-auto hidden scale-75 sm:inline"></i></a></li>
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-b border-black/10 px-3 text-xs hover:bg-[#252932] hover:text-white sm:justify-start"><i class="w-3 text-center fa-solid fa-location-dot"></i><span class="hidden sm:inline">Maps</span><i class="fa-regular fa-square-plus ml-auto hidden scale-75 sm:inline"></i></a></li>
        <li><a href="#" class="flex h-10 items-center justify-center gap-3 border-b border-black/10 px-3 text-xs hover:bg-[#252932] hover:text-white sm:justify-start"><i class="w-3 text-center fa-solid fa-sitemap"></i><span class="hidden sm:inline">Navigation Levels</span><i class="fa-regular fa-square-plus ml-auto hidden scale-75 sm:inline"></i></a></li>
    </ul></nav>
</aside>

<main class="ml-16 flex min-h-screen flex-col bg-[#f2f3f3] pt-10 sm:ml-40">
    <div class="flex h-6 items-center gap-2 border-b border-[#e1e3e4] bg-[#fafafa] px-3 text-xs text-[#777c83]"><span>Home</span><i class="fa-solid fa-chevron-right scale-75"></i><span>Dashboard</span></div>
    <section class="flex-1" aria-label="Empty dashboard content"></section>
</main>
</body>
</html>
