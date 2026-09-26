package employeemanagement.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final Properties props = new Properties();

    // Static block runs once, when the class is first loaded.
    // It reads db.properties from the classpath so we never
    // hardcode credentials directly in this file.
    static {
        try (InputStream input = DBConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("db.properties not found on classpath");
            }
            props.load(input);

            // Explicitly load the JDBC driver class named in the properties file.
            Class.forName(props.getProperty("db.driver"));

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database configuration", e);
        }
    }

    // Every DAO will call this method to get a fresh connection.
    // We return a new Connection each time rather than sharing one,
    // since JDBC Connections are not safe to share across requests.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.username"),
                props.getProperty("db.password")
        );
    }
}