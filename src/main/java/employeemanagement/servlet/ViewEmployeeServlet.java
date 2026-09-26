package employeemanagement.servlet;

import employeemanagement.model.Employee;
import employeemanagement.service.EmployeeService;
import employeemanagement.service.EmployeeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/employees.html")
public class ViewEmployeeServlet extends HttpServlet {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>Employees</title>");
        out.println("<link rel='stylesheet' href='css/style.css'></head><body>");
        out.println("<h1>All Employees</h1>");
        out.println("<a href='dashboard.html'>Back to Dashboard</a> | <a href='add-employee.html'>Add Employee</a>");

        try {
            List<Employee> employees = employeeService.getAllEmployees();

            if (employees.isEmpty()) {
                out.println("<p>No employees found.</p>");
            } else {
                out.println("<table border='1' cellpadding='8' cellspacing='0'>");
                out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th>" +
                             "<th>Department</th><th>Designation</th><th>Salary</th><th>Joining Date</th></tr>");

                for (Employee e : employees) {
                    out.println("<tr>");
                    out.println("<td>" + e.getId() + "</td>");
                    out.println("<td>" + e.getName() + "</td>");
                    out.println("<td>" + e.getEmail() + "</td>");
                    out.println("<td>" + e.getPhone() + "</td>");
                    out.println("<td>" + e.getDepartment() + "</td>");
                    out.println("<td>" + e.getDesignation() + "</td>");
                    out.println("<td>" + e.getSalary() + "</td>");
                    out.println("<td>" + e.getJoiningDate() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
        } catch (SQLException e) {
            out.println("<p style='color:red;'>A server error occurred while loading employees.</p>");
        }

        out.println("</body></html>");
    }
}