package employeemanagement.dao;

import employeemanagement.model.Employee;
import employeemanagement.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public void addEmployee(Employee e) throws SQLException {
        String sql = "INSERT INTO employees (name, email, phone, department, designation, salary, joining_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // try-with-resources: Connection and PreparedStatement are
        // automatically closed when this block ends, even if an
        // exception is thrown — prevents connection leaks.
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPhone());
            ps.setString(4, e.getDepartment());
            ps.setString(5, e.getDesignation());
            ps.setBigDecimal(6, e.getSalary());
            ps.setDate(7, Date.valueOf(e.getJoiningDate()));

            ps.executeUpdate();
        }
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employees ORDER BY id";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                employees.add(mapRow(rs));
            }
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null; // no employee found with this id
    }

    @Override
    public void updateEmployee(Employee e) throws SQLException {
        String sql = "UPDATE employees SET name=?, email=?, phone=?, department=?, " +
                     "designation=?, salary=?, joining_date=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPhone());
            ps.setString(4, e.getDepartment());
            ps.setString(5, e.getDesignation());
            ps.setBigDecimal(6, e.getSalary());
            ps.setDate(7, Date.valueOf(e.getJoiningDate()));
            ps.setInt(8, e.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Employee> searchEmployees(String keyword) throws SQLException {
        String sql = "SELECT * FROM employees WHERE name LIKE ? OR email LIKE ? OR department LIKE ?";
        List<Employee> employees = new ArrayList<>();
        String pattern = "%" + keyword + "%";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapRow(rs));
                }
            }
        }
        return employees;
    }

    @Override
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM employees WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true if a row was found
            }
        }
    }

    // Shared helper: converts one ResultSet row into an Employee object.
    // Avoids duplicating this mapping logic in every query method.
    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getInt("id"));
        e.setName(rs.getString("name"));
        e.setEmail(rs.getString("email"));
        e.setPhone(rs.getString("phone"));
        e.setDepartment(rs.getString("department"));
        e.setDesignation(rs.getString("designation"));
        e.setSalary(rs.getBigDecimal("salary"));
        e.setJoiningDate(rs.getDate("joining_date").toLocalDate());
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) e.setCreatedAt(ts.toLocalDateTime());
        return e;
    }
}
