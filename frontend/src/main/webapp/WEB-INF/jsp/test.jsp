<%! private final String title = "Chat"; %>
<%@ include file="header.jsp" %>
<body class="font-sans text-gray-800">
    <!-- Header with Navigation -->
    <header class="bg-gray-100 shadow">
        <div class="max-w-6xl mx-auto px-4 py-4 flex justify-between items-center">
            <div class="text-2xl font-bold text-blue-800">Beautiful Savior Lutheran Church</div>
            <nav class="space-x-6">
                <a href="#" class="hover:text-blue-600">Home</a>
                <a href="#" class="hover:text-blue-600">About</a>
                <a href="#" class="hover:text-blue-600">Sermons</a>
                <a href="#" class="hover:text-blue-600">Events</a>
                <a href="#" class="hover:text-blue-600">Contact</a>
            </nav>
        </div>
    </header>

    <!-- Hero Section -->
    <section class="bg-cover bg-center h-96 flex items-center justify-center" style="background-image: url('https://source.unsplash.com/random/1200x600?church')">
        <div class="text-center text-white bg-black bg-opacity-50 p-6 rounded">
            <h1 class="text-4xl font-bold mb-4">Welcome to Our Church</h1>
            <p class="text-lg">A place to worship, connect, and grow in faith.</p>
            <button class="mt-6 bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700">Join Us Sunday</button>
        </div>
    </section>

    <!-- Service Times -->
    <section class="max-w-6xl mx-auto px-4 py-12">
        <h2 class="text-3xl font-semibold text-center mb-8">Service Times</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div class="bg-gray-50 p-6 rounded shadow text-center">
                <h3 class="text-xl font-bold text-blue-800">Sunday Worship</h3>
                <p class="mt-2">10:00 AM</p>
            </div>
            <div class="bg-gray-50 p-6 rounded shadow text-center">
                <h3 class="text-xl font-bold text-blue-800">Bible Study Wednesday in the Word</h3>
                <p class="mt-2">7:00 PM</p>
            </div>
        </div>
    </section>

    <!-- About Section -->
    <section class="bg-gray-100 py-12">
        <div class="max-w-6xl mx-auto px-4 flex flex-col md:flex-row items-center">
            <div class="md:w-1/2">
                <img src="https://source.unsplash.com/random/600x400?prayer" alt="Church community" class="rounded shadow">
            </div>
            <div class="md:w-1/2 mt-6 md:mt-0 md:ml-8">
                <h2 class="text-3xl font-semibold mb-4">Who We Are</h2>
                <p class="text-lg">We are a family of believers dedicated to sharing the love of Christ. Join us as we grow together in faith, service, and community.</p>
                <a href="#" class="mt-4 inline-block text-blue-600 hover:underline">Learn More</a>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-blue-800 text-white py-8">
        <div class="max-w-6xl mx-auto px-4 grid grid-cols-1 md:grid-cols-3 gap-8">
            <div>
                <h4 class="text-lg font-bold mb-4">Contact Us</h4>
                <p>1337 W. 11th Street<br>Tempe, AZ 85281<br>(480) 967-2660<br>office@beautifulsaviortempe.org </p>
            </div>
            <div>
                <h4 class="text-lg font-bold mb-4">Quick Links</h4>
                <ul class="space-y-2">
                    <li><a href="#" class="hover:underline">Give Online</a></li>
                    <li><a href="#" class="hover:underline">Prayer Requests</a></li>
                    <li><a href="#" class="hover:underline">Newsletter</a></li>
                </ul>
            </div>
            <div>
                <h4 class="text-lg font-bold mb-4">Connect</h4>
                <p>Follow us on social media for updates and inspiration.</p>
                <div class="mt-4 flex space-x-4">
                    <a href="#" class="text-white hover:text-gray-300">Facebook</a>
                    <a href="#" class="text-white hover:text-gray-300">Twitter</a>
                    <a href="#" class="text-white hover:text-gray-300">Instagram</a>
                </div>
            </div>
        </div>
        <div class="text-center mt-6 text-sm">© <?php echo  ?> Beautiful Savior Lutheran Church. All rights reserved.</div>
    </footer>
</body>