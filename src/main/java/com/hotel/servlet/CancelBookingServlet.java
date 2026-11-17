
package com.hotel.servlet;

import com.hotel.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Object userIdObject =
                request.getSession().getAttribute("userId");

        if (userIdObject == null) {
            response.sendRedirect("login.jsp?error=login");
            return;
        }

        try {
            int userId = (Integer) userIdObject;
            int bookingId = Integer.parseInt(
                    request.getParameter("bookingId"));

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    throw new ServletException(
                            "Database connection failed.");
                }

                con.setAutoCommit(false);

                try {
                    String bookingSql =
                            "UPDATE bookings SET booking_status='Cancelled' "
                            + "WHERE booking_id=? AND user_id=? "
                            + "AND booking_status='Confirmed'";

                    int rows;

                    try (PreparedStatement ps =
                            con.prepareStatement(bookingSql)) {

                        ps.setInt(1, bookingId);
                        ps.setInt(2, userId);
                        rows = ps.executeUpdate();
                    }

                    if (rows == 0) {
                        con.rollback();
                        response.sendRedirect(
                                "myBookings?error=failed");
                        return;
                    }

                    String paymentSql =
                            "UPDATE payments SET payment_status='Cancelled' "
                            + "WHERE booking_id=? AND payment_status='Pending'";

                    try (PreparedStatement ps =
                            con.prepareStatement(paymentSql)) {

                        ps.setInt(1, bookingId);
                        ps.executeUpdate();
                    }

                    con.commit();

                    response.sendRedirect(
                            "myBookings?success=cancelled");

                } catch (SQLException e) {
                    con.rollback();
                    throw e;
                } finally {
                    con.setAutoCommit(true);
                }
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("myBookings?error=failed");
        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to cancel booking.", e);
        }
    }
}
