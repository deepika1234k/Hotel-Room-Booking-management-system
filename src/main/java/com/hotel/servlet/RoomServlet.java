
package com.hotel.servlet;

import com.hotel.dao.DBConnection;
import com.hotel.model.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Room> roomList = new ArrayList<>();

        String sql = "SELECT * FROM rooms WHERE availability = 'Available'";

       Connection connection = DBConnection.getConnection();

if (connection == null) {
    throw new ServletException("MySQL connection failed.");
}

try (Connection con = connection;
     PreparedStatement ps = con.prepareStatement(sql);
     ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Room room = new Room(
                    rs.getInt("room_id"),
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getBigDecimal("price"),
                    rs.getString("facilities"),
                    rs.getString("availability")
                );

                roomList.add(room);
            }

            request.setAttribute("roomList", roomList);

            request.getRequestDispatcher("rooms.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Unable to display rooms.", e);
        }
    }
}
