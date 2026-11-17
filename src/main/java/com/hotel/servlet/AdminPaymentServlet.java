
package com.hotel.servlet;

import com.hotel.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/adminPayments")
public class AdminPaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        List<Map<String, Object>> payments = new ArrayList<>();

        String sql = "SELECT p.payment_id, p.booking_id, p.amount, "
                + "p.payment_date, p.payment_status, u.name "
                + "FROM payments p "
                + "JOIN bookings b ON p.booking_id = b.booking_id "
                + "JOIN users u ON b.user_id = u.user_id "
                + "ORDER BY p.payment_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> payment = new HashMap<>();

                payment.put("paymentId", rs.getInt("payment_id"));
                payment.put("bookingId", rs.getInt("booking_id"));
                payment.put("amount", rs.getBigDecimal("amount"));
                payment.put("paymentDate", rs.getDate("payment_date"));
                payment.put("paymentStatus",
                        rs.getString("payment_status"));
                payment.put("customerName", rs.getString("name"));

                payments.add(payment);
            }

            request.setAttribute("payments", payments);

            request.getRequestDispatcher("adminPayments.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to retrieve payment records.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        try {
            int paymentId = Integer.parseInt(
                    request.getParameter("paymentId"));

            String status = request.getParameter("paymentStatus");

            if (!"Paid".equals(status) && !"Pending".equals(status)) {
                response.sendRedirect("adminPayments?error=invalid");
                return;
            }

            String sql = "UPDATE payments SET payment_status=? "
                    + "WHERE payment_id=? AND payment_status <> 'Cancelled'";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, status);
                ps.setInt(2, paymentId);
                ps.executeUpdate();
            }

            response.sendRedirect("adminPayments?success=updated");

        } catch (NumberFormatException e) {
            response.sendRedirect("adminPayments?error=invalid");
        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to update payment status.", e);
        }
    }
}
