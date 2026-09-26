package employeemanagement.servlet;

import employeemanagement.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Servlet Test</title></head><body>");
        out.println("<h1>Servlet is working!</h1>");

        // Temporary DB connection test — will be removed once DAO layer exists.
        try (Connection conn = DBConnection.getConnection()) {
            out.println("<p style='color:green;'>Database connection successful.</p>");
        } catch (SQLException e) {
            out.println("<p style='color:red;'>Database connection FAILED: " + e.getMessage() + "</p>");
        }

        out.println("<a href='index.html'>Back</a>");
        out.println("</body></html>");
    }
}