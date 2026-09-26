package employeemanagement.servlet;

import employeemanagement.model.User;
import employeemanagement.service.UserService;
import employeemanagement.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.authenticate(username, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("dashboard.html");
            } else {
                showLoginWithError(response, "Invalid username or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // logged for debugging — never shown to the user
            showLoginWithError(response, "A server error occurred. Please try again.");
        } catch (IllegalArgumentException e) {
            showLoginWithError(response, e.getMessage());
        }
    }

    private void showLoginWithError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>Login - Employee Management System</title>");
        out.println("<link rel='stylesheet' href='css/style.css'></head><body>");
        out.println("<h1>Admin Login</h1>");

        out.println("<p class='error'>" + message + "</p>");

        out.println("<form action='login' method='post'>");
        out.println("<label for='username'>Username</label><br>");
        out.println("<input type='text' id='username' name='username' required><br><br>");
        out.println("<label for='password'>Password</label><br>");
        out.println("<input type='password' id='password' name='password' required><br><br>");
        out.println("<button type='submit'>Login</button>");
        out.println("</form>");

        out.println("</body></html>");
    }
}