package employeemanagement.service;

import employeemanagement.dao.EmployeeDAO;
import employeemanagement.dao.EmployeeDAOImpl;
import employeemanagement.model.Employee;
import employeemanagement.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Override
    public void addEmployee(Employee employee) throws SQLException, IllegalArgumentException {
        validate(employee);

        // Business rule: no two employees can share an email.
        // This check happens here (not just as a DB UNIQUE constraint)
        // so we can give a clear, specific error message before hitting SQL.
        if (employeeDAO.emailExists(employee.getEmail())) {
            throw new IllegalArgumentException("An employee with this email already exists.");
        }

        employeeDAO.addEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID.");
        }
        return employeeDAO.getEmployeeById(id);
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException, IllegalArgumentException {
        validate(employee);

        Employee existing = employeeDAO.getEmployeeById(employee.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Employee not found.");
        }

        // Only block the email if it belongs to a DIFFERENT employee.
        // Otherwise updating a record without changing its own email would
        // incorrectly trigger a "duplicate email" error against itself.
        if (!existing.getEmail().equalsIgnoreCase(employee.getEmail())
                && employeeDAO.emailExists(employee.getEmail())) {
            throw new IllegalArgumentException("An employee with this email already exists.");
        }

        employeeDAO.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID.");
        }
        employeeDAO.deleteEmployee(id);
    }

    @Override
    public List<Employee> searchEmployees(String keyword) throws SQLException {
        if (!ValidationUtil.isNotEmpty(keyword)) {
            return getAllEmployees(); // empty search = show everything
        }
        return employeeDAO.searchEmployees(keyword.trim());
    }

    // Shared validation for both add and update.
    private void validate(Employee e) {
        if (!ValidationUtil.isNotEmpty(e.getName())) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (!ValidationUtil.isValidEmail(e.getEmail())) {
            throw new IllegalArgumentException("A valid email is required.");
        }
        if (!ValidationUtil.isValidPhone(e.getPhone())) {
            throw new IllegalArgumentException("A valid 10-digit phone number is required.");
        }
        if (!ValidationUtil.isNotEmpty(e.getDepartment())) {
            throw new IllegalArgumentException("Department is required.");
        }
        if (!ValidationUtil.isNotEmpty(e.getDesignation())) {
            throw new IllegalArgumentException("Designation is required.");
        }
        if (!ValidationUtil.isValidSalary(e.getSalary())) {
            throw new IllegalArgumentException("Salary must be a positive number.");
        }
        if (e.getJoiningDate() == null) {
            throw new IllegalArgumentException("Joining date is required.");
        }
    }
}