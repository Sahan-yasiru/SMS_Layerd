package org.example.smslayerd.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {
    private Connection connection;
    private static DBController dbController;

    private DBController() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SMS", "root", "082004");
    }

    public static DBController getInstance() throws SQLException {
        return dbController == null ? new DBController() : dbController;
    }

    public Connection getConnection() {
        return connection;
    }

}
