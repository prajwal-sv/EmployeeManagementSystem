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

@WebServlet("/edit-employee")
public class EditEmployeeServlet extends HttpServlet {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            int id = Integer.parseInt(idParam);
            Employee e = employeeService.getEmployeeById(id);

            if (e == null) {
                out.println("<!DOCTYPE html><html><head><link rel='stylesheet' href='css/style.css'></head><body>");
                out.println("<h1>Employee not found</h1>");
                out.println("<p class='error'>No employee exists with that ID.</p>");
                out.println("<a href='employees.html'>Back to list</a></body></html>");
                return;
            }

            out.println("<!DOCTYPE html><html><head><title>Edit Employee</title>");
            out.println("<link rel='stylesheet' href='css/style.css'></head><body>");
            out.println("<h1>Edit Employee</h1>");

            out.println("<nav>");
            out.println("<a href='dashboard.html'>Dashboard</a>");
            out.println("<a href='employees.html'>Employees</a>");
            out.println("<a href='add-employee.html'>Add Employee</a>");
            out.println("<a href='logout'>Logout</a>");
            out.println("</nav>");

            out.println("<form action='edit-employee' method='post'>");
            out.println("<input type='hidden' name='id' value='" + e.getId() + "'>");

            out.println("<label>Name</label><br><input type='text' name='name' value='" + e.getName() + "' required><br><br>");
            out.println("<label>Email</label><br><input type='email' name='email' value='" + e.getEmail() + "' required><br><br>");
            out.println("<label>Phone</label><br><input type='text' name='phone' value='" + e.getPhone() + "' required><br><br>");
            out.println("<label>Department</label><br><input type='text' name='department' value='" + e.getDepartment() + "' required><br><br>");
            out.println("<label>Designation</label><br><input type='text' name='designation' value='" + e.getDesignation() + "' required><br><br>");
            out.println("<label>Salary</label><br><input type='number' step='0.01' name='salary' value='" + e.getSalary() + "' required><br><br>");
            out.println("<label>Joining Date</label><br><input type='date' name='joiningDate' value='" + e.getJoiningDate() + "' required><br><br>");

            out.println("<button type='submit'>Update Employee</button>");
            out.println("</form>");

        } catch (NumberFormatException e) {
            out.println("<!DOCTYPE html><html><head><link rel='stylesheet' href='css/style.css'></head><body>");
            out.println("<h1>Invalid employee ID</h1>");
            out.println("<p class='error'>The employee ID in the URL is not valid.</p>");
            out.println("<a href='employees.html'>Back</a></body></html>");
        } catch (SQLException e) {
            e.printStackTrace(); // logged for debugging — never shown to the user
            out.println("<!DOCTYPE html><html><head><link rel='stylesheet' href='css/style.css'></head><body>");
            out.println("<h1>Server error</h1>");
            out.println("<p class='error'>A server error occurred while loading this employee.</p>");
            out.println("<a href='employees.html'>Back</a></body></html>");
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");
        String designation = request.getParameter("designation");
        String salaryStr = request.getParameter("salary");
        String joiningDateStr = request.getParameter("joiningDate");

        if (idParam == null || name == null || email == null || phone == null ||
            department == null || designation == null || salaryStr == null || joiningDateStr == null) {
            showError(response, "Missing required form data.");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            BigDecimal salary = new BigDecimal(salaryStr);
            LocalDate joiningDate = LocalDate.parse(joiningDateStr);

            Employee employee = new Employee(name, email, phone, department, designation, salary, joiningDate);
            employee.setId(id);

            employeeService.updateEmployee(employee);
            response.sendRedirect("employees.html");

        } catch (NumberFormatException e) {
            showError(response, "Salary must be a valid number.");
        } catch (java.time.format.DateTimeParseException e) {
            showError(response, "Joining date must be a valid date.");
        } catch (IllegalArgumentException e) {
            showError(response, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace(); // logged for debugging — never shown to the user
            showError(response, "A server error occurred while updating the employee.");
        }
    }

    private void showError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><link rel='stylesheet' href='css/style.css'></head><body>");
        out.println("<h1>Could not update employee</h1>");
        out.println("<p class='error'>" + message + "</p>");
        out.println("<a href='employees.html'>Back</a></body></html>");
    }
}