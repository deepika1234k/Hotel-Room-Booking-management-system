
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
    <title>Add Room - Hotel Paradise</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Hotel Paradise</h1>
    <nav>
        <a href="adminDashboard.jsp">Dashboard</a>
        <a href="manageRooms">Manage Rooms</a>
    </nav>
</header>

<div class="container">
    <h2>Add New Room</h2>

    <% if ("invalid".equals(request.getParameter("error"))) { %>
        <p style="color:red;">Please enter valid room details.</p>
    <% } %>

    <form action="addRoom" method="post">

        <label>Room Number:</label><br>
        <input type="text" name="roomNumber" required>
        <br><br>

        <label>Room Type:</label><br>
        <select name="roomType" required>
            <option value="">Select Room Type</option>
            <option value="Single">Single</option>
            <option value="Double">Double</option>
            <option value="Deluxe">Deluxe</option>
            <option value="Suite">Suite</option>
        </select>
        <br><br>

        <label>Price Per Night (₹):</label><br>
        <input type="number" name="price" min="1" step="0.01" required>
        <br><br>

        <label>Facilities:</label><br>
        <textarea name="facilities" rows="4"
                  placeholder="Example: AC, Wi-Fi, TV"></textarea>
        <br><br>

        <label>Availability:</label><br>
        <select name="availability" required>
            <option value="Available">Available</option>
            <option value="Unavailable">Unavailable</option>
        </select>
        <br><br>

        <button type="submit">Add Room</button>

    </form>
</div>

</body>
</html>
