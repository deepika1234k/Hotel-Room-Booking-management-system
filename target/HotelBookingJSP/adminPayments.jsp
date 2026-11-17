
<%@ page import="java.util.*" %>

<%
if (session.getAttribute("role") == null ||
    !"admin".equals(session.getAttribute("role"))) {
    response.sendRedirect("adminLogin.jsp");
    return;
}

List<Map<String, Object>> payments =
    (List<Map<String, Object>>) request.getAttribute("payments");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Payment Management - Hotel Paradise</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .payment-container {
            width: 90%;
            margin: 30px auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #333;
            color: white;
        }

        button {
            padding: 7px 12px;
            cursor: pointer;
        }
    </style>
</head>

<body>

<div class="payment-container">

    <h1>Payment Management</h1>

    <% if ("updated".equals(request.getParameter("success"))) { %>
        <p style="color:green;">Payment status updated successfully!</p>
    <% } %>

    <% if ("invalid".equals(request.getParameter("error"))) { %>
        <p style="color:red;">Invalid payment details or status.</p>
    <% } %>

    <table>
        <tr>
            <th>Payment ID</th>
            <th>Booking ID</th>
            <th>Customer Name</th>
            <th>Amount</th>
            <th>Payment Date</th>
            <th>Payment Status</th>
            <th>Update Status</th>
        </tr>

        <% if (payments != null && !payments.isEmpty()) {
            for (Map<String, Object> payment : payments) { %>

            <tr>
                <td><%= payment.get("paymentId") %></td>
                <td><%= payment.get("bookingId") %></td>
                <td><%= payment.get("customerName") %></td>
                <td>₹<%= payment.get("amount") %></td>
                <td><%= payment.get("paymentDate") %></td>
                <td><%= payment.get("paymentStatus") %></td>

                <td>
                    <% if (!"Cancelled".equals(
                            payment.get("paymentStatus"))) { %>

                        <form action="adminPayments" method="post">

                            <input type="hidden"
                                   name="paymentId"
                                   value="<%= payment.get("paymentId") %>">

                            <select name="paymentStatus">
                                <option value="Pending"
                                    <%= "Pending".equals(
                                        payment.get("paymentStatus")
                                    ) ? "selected" : "" %>>
                                    Pending
                                </option>

                                <option value="Paid"
                                    <%= "Paid".equals(
                                        payment.get("paymentStatus")
                                    ) ? "selected" : "" %>>
                                    Paid
                                </option>
                            </select>

                            <button type="submit">Update</button>

                        </form>

                    <% } else { %>
                        Cancelled
                    <% } %>
                </td>
            </tr>

        <% }
        } else { %>

            <tr>
                <td colspan="7">No payment records found.</td>
            </tr>

        <% } %>

    </table>

    <p><a href="adminDashboard.jsp">Back to Dashboard</a></p>

</div>

</body>
</html>
