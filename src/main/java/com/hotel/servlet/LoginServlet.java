
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String sql = "SELECT user_id, name FROM users WHERE email=? AND password=?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                throw new ServletException("Database connection failed.");
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, email);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        HttpSession session = request.getSession();
                        request.changeSessionId();

                        session.setAttribute("userId", rs.getInt("user_id"));
                        session.setAttribute("userName", rs.getString("name"));

                        response.sendRedirect("rooms");

                    } else {
                        response.sendRedirect("login.jsp?error=invalid");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Login failed.", e);
        }
    }
}
