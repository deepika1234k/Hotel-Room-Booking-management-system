
package com.hotel.servlet;

import com.hotel.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loadRoom")
public class RoomDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        if (!"true".equals(request.getSession().getAttribute("admin"))) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));

            String sql = "SELECT * FROM rooms WHERE room_id=?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, roomId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Map<String, Object> room = new HashMap<>();

                        room.put("roomId", rs.getInt("room_id"));
                        room.put("roomNumber", rs.getString("room_number"));
                        room.put("roomType", rs.getString("room_type"));
                        room.put("price", rs.getDouble("price"));
                        room.put("facilities", rs.getString("facilities"));
                        room.put("availability", rs.getString("availability"));

                        request.setAttribute("room", room);

                        request.getRequestDispatcher("editRoom.jsp")
                               .forward(request, response);
                    } else {
                        response.sendRedirect("manageRooms?error=notfound");
                    }
                }
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("manageRooms?error=invalid");
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve room details.", e);
        }
    }
}

