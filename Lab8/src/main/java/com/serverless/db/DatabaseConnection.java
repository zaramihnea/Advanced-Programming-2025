package com.serverless.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        try {
            if (!DatabaseInitializer.initialize()) {
                throw new SQLException("Failed to initialize database");
            }
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return ConnectionPoolManager.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Error getting connection from pool: " + e.getMessage());
            return null;
        }
    }

    public void closeConnection() {
        ConnectionPoolManager.getInstance().closePool();
        System.out.println("Connection pool closed");
    }
}