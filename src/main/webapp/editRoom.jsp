
<%@ page import="java.util.Map" %>
<%
    if (!"true".equals(session.getAttribute("admin"))) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    Map<String, Object> room =
        (Map<String, Object>) request.getAttribute("room");

    if (room == null) {
        response.sendRedirect("manageRooms");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Room - Hotel Paradise</title>
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
    <h2>Edit Room Details</h2>

    <form action="editRoom" method="post">

        <input type="hidden" name="roomId"
               value="<%= room.get("roomId") %>">

        <label>Room Number:</label><br>
        <input type="text" name="roomNumber"
               value="<%= room.get("roomNumber") %>" required>
        <br><br>

        <label>Room Type:</label><br>
        <select name="roomType" required>
            <option value="Single"
                <%= "Single".equals(room.get("roomType")) ? "selected" : "" %>>
                Single
            </option>
            <option value="Double"
                <%= "Double".equals(room.get("roomType")) ? "selected" : "" %>>
                Double
            </option>
            <option value="Deluxe"
                <%= "Deluxe".equals(room.get("roomType")) ? "selected" : "" %>>
                Deluxe
            </option>
            <option value="Suite"
                <%= "Suite".equals(room.get("roomType")) ? "selected" : "" %>>
                Suite
            </option>
        </select>
        <br><br>

        <label>Price Per Night (₹):</label><br>
        <input type="number" name="price" min="1" step="0.01"
               value="<%= room.get("price") %>" required>
        <br><br>

        <label>Facilities:</label><br>
        <textarea name="facilities" rows="4"><%= room.get("facilities") %></textarea>
        <br><br>

        <label>Availability:</label><br>
        <select name="availability" required>
            <option value="Available"
                <%= "Available".equals(room.get("availability")) ? "selected" : "" %>>
                Available
            </option>
            <option value="Unavailable"
                <%= "Unavailable".equals(room.get("availability")) ? "selected" : "" %>>
                Unavailable
            </option>
        </select>
        <br><br>

        <button type="submit">Update Room</button>

    </form>
</div>

</body>
</html>
