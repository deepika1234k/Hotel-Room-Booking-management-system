
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Room" %>

<!DOCTYPE html>
<html>
<head>
    <title>Available Rooms - Hotel Paradise</title>
    <link rel="stylesheet" href="css/style.css">

    <style>
        .room-container {
            width: 90%;
            margin: 30px auto;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
            gap: 20px;
        }

        .room-card {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px #ccc;
        }

        .room-card h3 {
            color: #17324d;
        }

        .book-btn {
            display: inline-block;
            background: #17324d;
            color: white;
            padding: 10px 18px;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>

<body>

<header>
    <h1>Hotel Paradise</h1>
    <p>Find Your Perfect Room</p>
</header>

<nav>
    <a href="index.jsp">Home</a>
    <a href="rooms">Rooms</a>
    <a href="myBookings">My Bookings</a>
    <a href="login.jsp">Login</a>
</nav>

<h2 style="text-align:center; margin-top:30px;">
    Available Hotel Rooms
</h2>

<div class="room-container">

<%
    List<Room> roomList =
        (List<Room>) request.getAttribute("roomList");

    if (roomList != null && !roomList.isEmpty()) {

        for (Room room : roomList) {
%>

    <div class="room-card">

        <h3><%= room.getRoomType() %> Room</h3>

        <p><b>Room Number:</b>
            <%= room.getRoomNumber() %>
        </p>

        <p><b>Price:</b> ₹
            <%= room.getPrice() %>
        </p>

        <p><b>Facilities:</b>
            <%= room.getFacilities() %>
        </p>

        <p><b>Status:</b>
            <%= room.getAvailability() %>
        </p>

        <a class="book-btn"
           href="booking.jsp?roomId=<%= room.getRoomId() %>">
            Book Now
        </a>

    </div>

<%
        }

    } else {
%>

    <p>No rooms are currently available.</p>

<%
    }
%>

</div>

<footer>
    <p>© 2026 Hotel Paradise</p>
</footer>

</body>
</html>
