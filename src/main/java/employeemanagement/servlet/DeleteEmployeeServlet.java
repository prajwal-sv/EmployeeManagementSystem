package employeemanagement.servlet;

import employeemanagement.service.EmployeeService;
import employeemanagement.service.EmployeeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-employee")
public class DeleteEmployeeServlet extends HttpServlet {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        try {
            int id = Integer.parseInt(idParam);
            employeeService.deleteEmployee(id);
            response.sendRedirect("employees.html");

        } catch (NumberFormatException e) {
            response.sendRedirect("employees.html"); // invalid id, just go back to the list
        } catch (SQLException e) {
            response.sendRedirect("employees.html"); // could enhance later with an error message
        }
    }
}