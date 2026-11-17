
<%@ page import="java.util.List, java.util.Map" %>
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp?error=login");
        return;
    }

    List<Map<String, Object>> bookings =
        (List<Map<String, Object>>) request.getAttribute("bookings");
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Bookings - Hotel Paradise</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Hotel Paradise</h1>
    <p>My Booking History</p>
</header>

<nav>
    <a href="index.jsp">Home</a>
    <a href="rooms">Available Rooms</a>
    <a href="myBookings">My Bookings</a>
    <a href="logout">Logout</a>
</nav>

<div class="container">

    <h2>My Bookings</h2>

    <% if ("booked".equals(request.getParameter("success"))) { %>
        <p style="color:green;">Room booked successfully!</p>
    <% } else if ("cancelled".equals(request.getParameter("success"))) { %>
        <p style="color:green;">Booking cancelled successfully!</p>
    <% } else if ("failed".equals(request.getParameter("error"))) { %>
        <p style="color:red;">Unable to cancel this booking.</p>
    <% } %>

    <% if (bookings != null && !bookings.isEmpty()) {
        for (Map<String, Object> booking : bookings) { %>

        <div class="room-card">

            <h3>Booking ID: <%= booking.get("bookingId") %></h3>

            <p><strong>Room Number:</strong>
                <%= booking.get("roomNumber") %></p>

            <p><strong>Room Type:</strong>
                <%= booking.get("roomType") %></p>

            <p><strong>Price Per Night:</strong>
                ₹<%= booking.get("price") %></p>

            <p><strong>Check-in:</strong>
                <%= booking.get("checkIn") %></p>

            <p><strong>Check-out:</strong>
                <%= booking.get("checkOut") %></p>

            <p><strong>Total Amount:</strong>
                ₹<%= booking.get("amount") %></p>

            <p><strong>Booking Status:</strong>
                <%= booking.get("bookingStatus") %></p>

            <p><strong>Payment Status:</strong>
                <%= booking.get("paymentStatus") %></p>

            <% if ("Confirmed".equals(booking.get("bookingStatus"))) { %>

                <form action="cancelBooking" method="post">
                    <input type="hidden" name="bookingId"
                           value="<%= booking.get("bookingId") %>">

                    <button type="submit">Cancel Booking</button>
                </form>

            <% } %>

        </div>

    <% }
       } else { %>

        <p>You have not made any bookings yet.</p>
        <a href="rooms">Explore Available Rooms</a>

    <% } %>

</div>

<footer>
    <p>© 2026 Hotel Paradise. All Rights Reserved.</p>
</footer>

</body>
</html>
