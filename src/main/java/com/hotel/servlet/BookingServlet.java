
package com.hotel.servlet;

import com.hotel.dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

@WebServlet("/book")
public class BookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        int roomId = Integer.parseInt(request.getParameter("roomId"));

        Date checkIn = Date.valueOf(request.getParameter("checkIn"));
        Date checkOut = Date.valueOf(request.getParameter("checkOut"));

        if (!checkOut.after(checkIn) ||
                !checkIn.toLocalDate().isAfter(java.time.LocalDate.now().minusDays(1))) {
            response.sendRedirect("booking.jsp?error=dates");
            return;
        }

        String checkRoom = "SELECT room_id FROM rooms WHERE room_id=? AND availability='Available'";

        String insertBooking =
            "INSERT INTO bookings (user_id, room_id, check_in, check_out, booking_status) VALUES (?, ?, ?, ?, 'Confirmed')";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                throw new ServletException("Database connection failed.");
            }

            try (PreparedStatement check = con.prepareStatement(checkRoom)) {

                check.setInt(1, roomId);

                try (ResultSet rs = check.executeQuery()) {

                    if (!rs.next()) {
                        response.sendRedirect("rooms?error=unavailable");
                        return;
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(insertBooking)) {

                ps.setInt(1, userId);
                ps.setInt(2, roomId);
                ps.setDate(3, checkIn);
                ps.setDate(4, checkOut);

                ps.executeUpdate();

                response.sendRedirect("myBookings?success=booked");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Booking failed.", e);
        }
    }
}
