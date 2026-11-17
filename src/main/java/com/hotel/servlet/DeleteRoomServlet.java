
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

@WebServlet("/deleteRoom")
public class DeleteRoomServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        if (!"true".equals(request.getSession().getAttribute("admin"))) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));

            String sql = "DELETE FROM rooms WHERE room_id = ? "
                    + "AND NOT EXISTS "
                    + "(SELECT 1 FROM bookings WHERE bookings.room_id = ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, roomId);
                ps.setInt(2, roomId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    response.sendRedirect("manageRooms?success=deleted");
                } else {
                    response.sendRedirect("manageRooms?error=booked");
                }
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("manageRooms?error=invalid");
        } catch (SQLException e) {
            throw new ServletException("Unable to delete room.", e);
        }
    }
}

