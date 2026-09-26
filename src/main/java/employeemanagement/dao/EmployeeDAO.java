package employeemanagement.dao;

import employeemanagement.model.Employee;
import java.sql.SQLException;
import java.util.List;

// Interface defines WHAT operations exist; EmployeeDAOImpl defines HOW.
// This separation lets us swap implementations later without touching
// any code that depends on this interface.
public interface EmployeeDAO {
    void addEmployee(Employee employee) throws SQLException;
    List<Employee> getAllEmployees() throws SQLException;
    Employee getEmployeeById(int id) throws SQLException;
    void updateEmployee(Employee employee) throws SQLException;
    void deleteEmployee(int id) throws SQLException;
    List<Employee> searchEmployees(String keyword) throws SQLException;
    boolean emailExists(String email) throws SQLException;
}
