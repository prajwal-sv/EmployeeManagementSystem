package employeemanagement.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = DBConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input != null) {
                props.load(input);
            }

            Class.forName(getConfig("db.driver", "DB_DRIVER", "com.mysql.cj.jdbc.Driver"));

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database configuration", e);
        }
    }

    // Checks an environment variable FIRST (used in production on Render),
    // and falls back to db.properties (used for local development).
    // This is the "centralized config, easy to change at deployment" the
    // spec requires — no code changes needed to switch environments.
    private static String getConfig(String propertyKey, String envKey, String defaultValue) {
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isEmpty()) {
            return envValue;
        }
        return props.getProperty(propertyKey, defaultValue);
    }

    public static Connection getConnection() throws SQLException {
        String url = getConfig("db.url", "DB_URL", null);
        String username = getConfig("db.username", "DB_USERNAME", null);
        String password = getConfig("db.password", "DB_PASSWORD", null);

        return DriverManager.getConnection(url, username, password);
    }
}