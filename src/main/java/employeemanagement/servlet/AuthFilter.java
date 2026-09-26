package employeemanagement.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// Runs before every request to dashboard.html and any /employee* servlet.
// If there's no logged-in user in the session, redirect to login instead
// of letting the request through.
@WebFilter(urlPatterns = {"/dashboard.html", "/employees.html", "/add-employee.html"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (loggedIn) {
            chain.doFilter(req, res); // continue to the requested page
        } else {
            response.sendRedirect(request.getContextPath() + "/login.html");
        }
    }
}