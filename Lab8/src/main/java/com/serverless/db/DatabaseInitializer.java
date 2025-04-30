package com.serverless.db;

import com.serverless.config.DatabaseConfig;
import com.serverless.util.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DatabaseInitializer {
    public static boolean initialize() {
        Connection defaultConn = null;
        Connection appConn = null;

        try {
            defaultConn = DriverManager.getConnection(
                    DatabaseConfig.DEFAULT_URL,
                    DatabaseConfig.USER,
                    DatabaseConfig.PASSWORD
            );

            boolean dbExists = DBUtils.databaseExists(defaultConn, DatabaseConfig.APP_DB);

            if (!dbExists) {
                if (!DBUtils.createDatabase(defaultConn, DatabaseConfig.APP_DB)) {
                    return false;
                }
            }

            appConn = DriverManager.getConnection(
                    DatabaseConfig.APP_URL,
                    DatabaseConfig.USER,
                    DatabaseConfig.PASSWORD
            );

            boolean continentsExist = DBUtils.tableExists(appConn, DatabaseConfig.CONTINENT_TABLE);
            boolean countriesExist = DBUtils.tableExists(appConn, DatabaseConfig.COUNTRY_TABLE);

            if (!continentsExist || !countriesExist) {
                System.out.println("Creating database schema...");
                List<String> statements = DBUtils.loadSQLScript("db/schema.sql");

                List<String> schemaStatements = statements.stream()
                        .filter(stmt -> !stmt.toLowerCase().contains("create database") &&
                                !stmt.toLowerCase().startsWith("\\c"))
                        .toList();

                DBUtils.executeStatements(appConn, schemaStatements);
                System.out.println("Database schema created successfully");
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            return false;
        } finally {
            DBUtils.closeConnection(appConn);
            DBUtils.closeConnection(defaultConn);
        }
    }
}