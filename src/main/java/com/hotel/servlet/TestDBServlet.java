
package com.hotel.servlet;

import java.io.IOException;
import java.sql.Connection;

import com.hotel.dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/testdb")
public class TestDBServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                response.getWriter().println(
                    "<h2>MySQL Database Connected Successfully!</h2>"
                );
                con.close();
            } else {
                response.getWriter().println(
                    "<h2>Database Connection Failed!</h2>"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                "<h2>Connection Error. Check Tomcat logs.</h2>"
            );
        }
    }
}
