
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    if (!"true".equals(session.getAttribute("admin"))) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Hotel Paradise</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Hotel Paradise - Admin Dashboard</h1>
    <nav>
    <a href="index.jsp">Home</a>
    <a href="manageRooms">Manage Rooms</a>
    <a href="adminBookings">Manage Bookings</a>
    <a href="adminLogout">Logout</a>
</nav>
</header>

<div class="container">
    <h2>Welcome, Admin!</h2>
    <p>Manage the hotel rooms and customer bookings from here.</p>

    <div class="room-card">
        <h3>Room Management</h3>
        <p>Add, update, delete and view hotel rooms.</p>
        <a href="manageRooms">Manage Rooms</a>
    </div>

    <div class="room-card">
        <h3>Booking Management</h3>
        <p>View customer bookings and update booking and payment status.</p>
        <a href="adminBookings">Manage Bookings</a>
    </div>

    <div class="room-card">
        <h3>Customer Bookings</h3>
        <p>View the booking records of customers.</p>
        <a href="adminBookings">View Bookings</a>
    </div>

</div>

</body>
</html>
