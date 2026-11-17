
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

@WebServlet("/manageRooms")
public class ManageRoomsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        if (!"true".equals(request.getSession().getAttribute("admin"))) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        List<Map<String, Object>> rooms = new ArrayList<>();

        String sql = "SELECT * FROM rooms ORDER BY room_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> room = new HashMap<>();

                room.put("roomId", rs.getInt("room_id"));
                room.put("roomNumber", rs.getString("room_number"));
                room.put("roomType", rs.getString("room_type"));
                room.put("price", rs.getDouble("price"));
                room.put("facilities", rs.getString("facilities"));
                room.put("availability", rs.getString("availability"));

                rooms.add(room);
            }

            request.setAttribute("rooms", rooms);
            request.getRequestDispatcher("adminRooms.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve room details.", e);
        }
    }
}

