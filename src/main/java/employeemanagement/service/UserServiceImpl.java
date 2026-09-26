package employeemanagement.service;

import employeemanagement.dao.UserDAO;
import employeemanagement.dao.UserDAOImpl;
import employeemanagement.model.User;
import employeemanagement.util.ValidationUtil;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public User authenticate(String username, String password) throws SQLException {
        if (!ValidationUtil.isNotEmpty(username) || !ValidationUtil.isNotEmpty(password)) {
            throw new IllegalArgumentException("Username and password are required.");
        }

        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            return null; // no such user — Servlet will show "invalid login"
        }

        // TEMPORARY: plain-text comparison. We will replace this with proper
        // password hashing (e.g. BCrypt-style hashing) in Phase 8, since storing
        // or comparing plain-text passwords is explicitly disallowed by the spec.
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}