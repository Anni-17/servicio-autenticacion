package co.sena.kireikanri.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/kirei_kanri", "root", "");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error cargando el driver de MySQL", e);
        }
    }
}