package employeemanagement.dao;

import employeemanagement.model.User;
import java.sql.SQLException;

public interface UserDAO {
    User getUserByUsername(String username) throws SQLException;
}