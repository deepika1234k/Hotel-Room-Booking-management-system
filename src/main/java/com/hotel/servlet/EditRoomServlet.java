
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

@WebServlet("/editRoom")
public class EditRoomServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        if (!"true".equals(request.getSession().getAttribute("admin"))) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String roomNumber = request.getParameter("roomNumber");
            String roomType = request.getParameter("roomType");
            String facilities = request.getParameter("facilities");
            String availability = request.getParameter("availability");
            double price = Double.parseDouble(request.getParameter("price"));

            if (roomNumber == null || roomNumber.trim().isEmpty()
                    || roomType == null || roomType.trim().isEmpty()
                    || price <= 0) {
                response.sendRedirect("manageRooms?error=invalid");
                return;
            }

            String sql = "UPDATE rooms SET room_number=?, room_type=?, "
                    + "price=?, facilities=?, availability=? WHERE room_id=?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, roomNumber.trim());
                ps.setString(2, roomType);
                ps.setDouble(3, price);
                ps.setString(4, facilities);
                ps.setString(5, availability);
                ps.setInt(6, roomId);

                ps.executeUpdate();
            }

            response.sendRedirect("manageRooms?success=updated");

        } catch (NumberFormatException e) {
            response.sendRedirect("manageRooms?error=invalid");
        } catch (SQLException e) {
            throw new ServletException("Unable to update room.", e);
        }
    }
}

