
package com.hotel.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Demo admin credentials
        if ("admin".equals(username) &&
            "admin123".equals(password)) {

            HttpSession session = request.getSession();
            session.setAttribute("admin", "true");

            response.sendRedirect("adminDashboard.jsp");

        } else {
            response.sendRedirect("adminLogin.jsp?error=invalid");
        }
    }
}
