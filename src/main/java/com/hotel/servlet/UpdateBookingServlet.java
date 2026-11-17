
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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/updateBooking")
public class UpdateBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin login
        if (!"true".equals(request.getSession()
                                  .getAttribute("admin"))) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        String bookingStatus = request.getParameter("bookingStatus");
        String paymentStatus = request.getParameter("paymentStatus");

        // Validate booking status
        if (!"Confirmed".equals(bookingStatus)
                && !"Cancelled".equals(bookingStatus)
                && !"Completed".equals(bookingStatus)) {

            response.sendRedirect("adminBookings?error=invalid");
            return;
        }

        // Validate payment status
        if (!"Pending".equals(paymentStatus)
                && !"Paid".equals(paymentStatus)
                && !"Cancelled".equals(paymentStatus)) {

            response.sendRedirect("adminBookings?error=invalid");
            return;
        }

        try {
            int bookingId = Integer.parseInt(
                    request.getParameter("bookingId"));

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    throw new ServletException(
                            "Database connection failed.");
                }

                con.setAutoCommit(false);

                try {
                    // Check current booking status
                    String checkSql =
                            "SELECT booking_status FROM bookings "
                            + "WHERE booking_id=? FOR UPDATE";

                    String currentStatus;

                    try (PreparedStatement ps =
                            con.prepareStatement(checkSql)) {

                        ps.setInt(1, bookingId);

                        try (ResultSet rs = ps.executeQuery()) {

                            if (!rs.next()) {
                                con.rollback();
                                response.sendRedirect(
                                        "adminBookings?error=invalid");
                                return;
                            }

                            currentStatus =
                                    rs.getString("booking_status");
                        }
                    }

                    // Do not reactivate a cancelled booking
                    if ("Cancelled".equals(currentStatus)
                            && "Confirmed".equals(bookingStatus)) {

                        con.rollback();
                        response.sendRedirect(
                                "adminBookings?error=invalid");
                        return;
                    }

                    // Update booking status
                    String bookingSql =
                            "UPDATE bookings SET booking_status=? "
                            + "WHERE booking_id=?";

                    try (PreparedStatement ps =
                            con.prepareStatement(bookingSql)) {

                        ps.setString(1, bookingStatus);
                        ps.setInt(2, bookingId);
                        ps.executeUpdate();
                    }

                    // Update payment status and payment date
                    String paymentSql =
                            "UPDATE payments SET payment_status=?, "
                            + "payment_date=CASE "
                            + "WHEN ?='Paid' THEN CURRENT_DATE "
                            + "ELSE NULL END "
                            + "WHERE booking_id=?";

                    try (PreparedStatement ps =
                            con.prepareStatement(paymentSql)) {

                        ps.setString(1, paymentStatus);
                        ps.setString(2, paymentStatus);
                        ps.setInt(3, bookingId);
                        ps.executeUpdate();
                    }

                    con.commit();

                    response.sendRedirect(
                            "adminBookings?success=updated");

                } catch (SQLException e) {

                    con.rollback();
                    throw e;

                } finally {
                    con.setAutoCommit(true);
                }
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "adminBookings?error=invalid");

        } catch (SQLException e) {

            throw new ServletException(
                    "Unable to update booking details.", e);
        }
    }
}
