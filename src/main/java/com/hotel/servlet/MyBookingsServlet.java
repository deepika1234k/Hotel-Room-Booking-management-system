
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

@WebServlet("/myBookings")
public class MyBookingsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Object userIdObject =
                request.getSession().getAttribute("userId");

        if (userIdObject == null) {
            response.sendRedirect("login.jsp?error=login");
            return;
        }

        int userId = (Integer) userIdObject;

        List<Map<String, Object>> bookings = new ArrayList<>();

        String sql = "SELECT b.booking_id, r.room_number, "
                + "r.room_type, r.price, b.check_in, b.check_out, "
                + "b.booking_status, p.amount, p.payment_date, "
                + "p.payment_status "
                + "FROM bookings b "
                + "JOIN rooms r ON b.room_id = r.room_id "
                + "LEFT JOIN payments p ON b.booking_id = p.booking_id "
                + "WHERE b.user_id = ? "
                + "ORDER BY b.booking_id DESC";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                throw new ServletException("Database connection failed.");
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Map<String, Object> booking = new HashMap<>();

                        booking.put("bookingId",
                                rs.getInt("booking_id"));
                        booking.put("roomNumber",
                                rs.getString("room_number"));
                        booking.put("roomType",
                                rs.getString("room_type"));
                        booking.put("price",
                                rs.getDouble("price"));
                        booking.put("checkIn",
                                rs.getDate("check_in"));
                        booking.put("checkOut",
                                rs.getDate("check_out"));
                        booking.put("bookingStatus",
                                rs.getString("booking_status"));
                        booking.put("amount",
                                rs.getBigDecimal("amount"));
                        booking.put("paymentDate",
                                rs.getDate("payment_date"));
                        booking.put("paymentStatus",
                                rs.getString("payment_status"));

                        bookings.add(booking);
                    }
                }
            }

            request.setAttribute("bookings", bookings);

            request.getRequestDispatcher("mybookings.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to retrieve booking history.", e);
        }
    }
}
