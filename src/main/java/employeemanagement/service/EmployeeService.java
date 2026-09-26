package employeemanagement.service;

import employeemanagement.model.Employee;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employee) throws SQLException, IllegalArgumentException;
    List<Employee> getAllEmployees() throws SQLException;
    Employee getEmployeeById(int id) throws SQLException;
    void updateEmployee(Employee employee) throws SQLException, IllegalArgumentException;
    void deleteEmployee(int id) throws SQLException;
    List<Employee> searchEmployees(String keyword) throws SQLException;
}