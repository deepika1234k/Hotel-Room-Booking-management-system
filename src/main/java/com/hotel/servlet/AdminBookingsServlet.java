
package com.hotel.servlet;

import com.hotel.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/adminBookings")
public class AdminBookingsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        if (!"true".equals(request.getSession().getAttribute("admin"))) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        List<Map<String, Object>> bookings = new ArrayList<>();

        String sql = "SELECT b.booking_id, u.name, u.email, "
                + "r.room_number, r.room_type, "
                + "b.check_in, b.check_out, b.booking_status, "
                + "p.amount, p.payment_status "
                + "FROM bookings b "
                + "JOIN users u ON b.user_id = u.user_id "
                + "JOIN rooms r ON b.room_id = r.room_id "
                + "LEFT JOIN payments p ON b.booking_id = p.booking_id "
                + "ORDER BY b.booking_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> booking = new HashMap<>();

                booking.put("bookingId", rs.getInt("booking_id"));
                booking.put("customerName", rs.getString("name"));
                booking.put("email", rs.getString("email"));
                booking.put("roomNumber", rs.getString("room_number"));
                booking.put("roomType", rs.getString("room_type"));
                booking.put("checkIn", rs.getDate("check_in"));
                booking.put("checkOut", rs.getDate("check_out"));
                booking.put("bookingStatus", rs.getString("booking_status"));
                booking.put("amount", rs.getBigDecimal("amount"));
                booking.put("paymentStatus", rs.getString("payment_status"));

                bookings.add(booking);
            }

            request.setAttribute("bookings", bookings);

            request.getRequestDispatcher("adminBookings.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve booking details.", e);
        }
    }
}

