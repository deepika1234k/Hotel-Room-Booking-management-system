
<%@ page import="java.util.List, java.util.Map" %>
<%
    if (!"true".equals(session.getAttribute("admin"))) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    List<Map<String, Object>> bookings =
        (List<Map<String, Object>>) request.getAttribute("bookings");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Booking Management - Hotel Paradise</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Hotel Paradise - Booking Management</h1>
    <nav>
        <a href="adminDashboard.jsp">Dashboard</a>
        <a href="manageRooms">Manage Rooms</a>
        <a href="index.jsp">Home</a>
    </nav>
</header>

<div class="container">

    <h2>All Customer Bookings</h2>

    <% if ("updated".equals(request.getParameter("success"))) { %>
        <p style="color:green;">Booking details updated successfully!</p>
    <% } else if ("invalid".equals(request.getParameter("error"))) { %>
        <p style="color:red;">Invalid booking update.</p>
    <% } %>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Booking ID</th>
            <th>Customer</th>
            <th>Email</th>
            <th>Room Number</th>
            <th>Room Type</th>
            <th>Check-in</th>
            <th>Check-out</th>
            <th>Amount (₹)</th>
            <th>Booking Status</th>
            <th>Payment Status</th>
            <th>Update</th>
        </tr>

        <% if (bookings != null && !bookings.isEmpty()) {
            for (Map<String, Object> booking : bookings) { %>

        <tr>
            <td><%= booking.get("bookingId") %></td>
            <td><%= booking.get("customerName") %></td>
            <td><%= booking.get("email") %></td>
            <td><%= booking.get("roomNumber") %></td>
            <td><%= booking.get("roomType") %></td>
            <td><%= booking.get("checkIn") %></td>
            <td><%= booking.get("checkOut") %></td>
            <td><%= booking.get("amount") %></td>
            <td><%= booking.get("bookingStatus") %></td>
            <td><%= booking.get("paymentStatus") %></td>

            <td>
                <form action="updateBooking" method="post">

                    <input type="hidden" name="bookingId"
                           value="<%= booking.get("bookingId") %>">

                    <label>Booking:</label>
                    <select name="bookingStatus">
                        <option value="Confirmed"
                            <%= "Confirmed".equals(booking.get("bookingStatus")) ? "selected" : "" %>>
                            Confirmed
                        </option>
                        <option value="Cancelled"
                            <%= "Cancelled".equals(booking.get("bookingStatus")) ? "selected" : "" %>>
                            Cancelled
                        </option>
                        <option value="Completed"
                            <%= "Completed".equals(booking.get("bookingStatus")) ? "selected" : "" %>>
                            Completed
                        </option>
                    </select>

                    <br><br>

                    <label>Payment:</label>
                    <select name="paymentStatus">
                        <option value="Pending"
                            <%= "Pending".equals(booking.get("paymentStatus")) ? "selected" : "" %>>
                            Pending
                        </option>
                        <option value="Paid"
                            <%= "Paid".equals(booking.get("paymentStatus")) ? "selected" : "" %>>
                            Paid
                        </option>
                        <option value="Cancelled"
    <%= "Cancelled".equals(booking.get("paymentStatus")) ? "selected" : "" %>>
    Cancelled
</option>
                    </select>

                    <br><br>

                    <button type="submit">Update</button>

                </form>
            </td>
        </tr>

        <% }
           } else { %>

        <tr>
            <td colspan="11">No bookings found.</td>
        </tr>

        <% } %>

    </table>

</div>

</body>
</html>
