package employeemanagement.servlet;

import employeemanagement.model.Employee;
import employeemanagement.service.EmployeeService;
import employeemanagement.service.EmployeeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/add-employee")
public class AddEmployeeServlet extends HttpServlet {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("add-employee.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");
        String designation = request.getParameter("designation");
        String salaryStr = request.getParameter("salary");
        String joiningDateStr = request.getParameter("joiningDate");

        if (name == null || email == null || phone == null || department == null ||
            designation == null || salaryStr == null || joiningDateStr == null) {
            showError(response, "Missing required form data.");
            return;
        }

        try {
            BigDecimal salary = new BigDecimal(salaryStr);
            LocalDate joiningDate = LocalDate.parse(joiningDateStr);

            Employee employee = new Employee(name, email, phone, department, designation, salary, joiningDate);
            employeeService.addEmployee(employee);

            response.sendRedirect("employees.html");

        } catch (NumberFormatException e) {
            showError(response, "Salary must be a valid number.");
        } catch (java.time.format.DateTimeParseException e) {
            showError(response, "Joining date must be a valid date.");
        } catch (IllegalArgumentException e) {
            showError(response, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace(); // logged for debugging — never shown to the user
            showError(response, "A server error occurred while saving the employee. Please try again.");
        }
    }

    private void showError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><title>Error</title>");
        out.println("<link rel='stylesheet' href='css/style.css'></head><body>");
        out.println("<h1>Could not add employee</h1>");

        out.println("<nav>");
        out.println("<a href='dashboard.html'>Dashboard</a>");
        out.println("<a href='employees.html'>Employees</a>");
        out.println("<a href='add-employee.html'>Add Employee</a>");
        out.println("<a href='logout'>Logout</a>");
        out.println("</nav>");

        out.println("<p class='error'>" + message + "</p>");
        out.println("<a href='add-employee.html'>Try again</a>");
        out.println("</body></html>");
    }
}