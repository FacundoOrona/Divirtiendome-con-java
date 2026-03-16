package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/apivanilla_db";
    private static final String USER = "root"; // tu usuario MySQL
    private static final String PASSWORD = "root";

    public static Connection getConection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}