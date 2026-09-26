package employeemanagement.servlet;

import employeemanagement.model.User;
import employeemanagement.service.UserService;
import employeemanagement.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    // Show the login page when the user navigates here directly.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.html").forward(request, response);
    }

    // Handle the actual login form submission.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.authenticate(username, password);

            if (user != null) {
                // Create (or reuse) an HttpSession and store the logged-in user.
                // Servlets/pages later check session.getAttribute("user") to
                // confirm someone is actually logged in.
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("dashboard.html");
            } else {
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("login.html").forward(request, response);
            }

        } catch (SQLException e) {
            // Never expose raw SQL error details to the user (spec requirement).
            request.setAttribute("error", "A server error occurred. Please try again.");
            request.getRequestDispatcher("login.html").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("login.html").forward(request, response);
        }
    }
}