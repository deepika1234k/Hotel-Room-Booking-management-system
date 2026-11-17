
<%
if (session.getAttribute("userId") == null) {
    response.sendRedirect("login.jsp");
    return;
}

String roomId = request.getParameter("roomId");

if (roomId == null || !roomId.matches("\\d+")) {
    response.sendRedirect("rooms");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>Book a Room - Hotel Paradise</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1>Book Your Room</h1>

    <% if ("dates".equals(request.getParameter("error"))) { %>
        <p style="color:red;">Please select valid check-in and check-out dates.</p>
    <% } %>

    <form action="book" method="post">

        <input type="hidden" name="roomId"
               value="<%= roomId %>">

        <label>Check-in Date:</label>
        <input type="date" name="checkIn" required>

        <label>Check-out Date:</label>
        <input type="date" name="checkOut" required>

        <button type="submit">Confirm Booking</button>

    </form>

    <p><a href="rooms">Back to Rooms</a></p>
</div>

</body>
</html>
