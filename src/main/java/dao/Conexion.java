package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Ajusta host/puerto si fuera necesario
    private static final String URL = "jdbc:mysql://localhost:3306/lab_estructura?useSSL=false&serverTimezone=UTC";
    private static final String USER = "emerson";
    private static final String PASS = "9029";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
