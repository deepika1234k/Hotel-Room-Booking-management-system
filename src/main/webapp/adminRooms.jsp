
<%@ page import="java.util.List, java.util.Map" %>
<%
    if (!"true".equals(session.getAttribute("admin"))) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    List<Map<String, Object>> rooms =
        (List<Map<String, Object>>) request.getAttribute("rooms");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Rooms - Hotel Paradise</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Hotel Paradise - Room Management</h1>
    <nav>
        <a href="adminDashboard.jsp">Dashboard</a>
        <a href="index.jsp">Home</a>
    </nav>
</header>

<div class="container">
    <h2>Manage Hotel Rooms</h2>

<%
    String success = request.getParameter("success");
    String error = request.getParameter("error");

    if ("added".equals(success)) {
%>
    <p style="color:green;">Room added successfully!</p>
<% } else if ("updated".equals(success)) { %>
    <p style="color:green;">Room updated successfully!</p>
<% } else if ("deleted".equals(success)) { %>
    <p style="color:green;">Room deleted successfully!</p>
<% } else if ("booked".equals(error)) { %>
    <p style="color:red;">This room has booking records and cannot be deleted.</p>
<% } else if ("invalid".equals(error)) { %>
    <p style="color:red;">Invalid room details. Please check your input.</p>
<% } else if ("notfound".equals(error)) { %>
    <p style="color:red;">Room not found.</p>
<% } %>


    <p><a href="addRoom.jsp">Add New Room</a></p>

    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>Room ID</th>
            <th>Room Number</th>
            <th>Room Type</th>
            <th>Price</th>
            <th>Facilities</th>
            <th>Availability</th>
            <th>Actions</th>
        </tr>

        <% if (rooms != null && !rooms.isEmpty()) {
            for (Map<String, Object> room : rooms) { %>

        <tr>
            <td><%= room.get("roomId") %></td>
            <td><%= room.get("roomNumber") %></td>
            <td><%= room.get("roomType") %></td>
            <td><%= room.get("price") %></td>
            <td><%= room.get("facilities") %></td>
            <td><%= room.get("availability") %></td>
            <td>
                <a href="loadRoom?roomId=<%= room.get("roomId") %>">Edit</a>
                |
                <form action="deleteRoom" method="post" style="display:inline;">
                    <input type="hidden" name="roomId"
                           value="<%= room.get("roomId") %>">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>

        <% }
           } else { %>
        <tr>
            <td colspan="7">No rooms found.</td>
        </tr>
        <% } %>
    </table>

</div>
</body>
</html>
