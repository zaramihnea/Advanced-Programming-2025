package com.serverless.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public static boolean databaseExists(Connection conn, String dbName) {
        String sql = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking if database exists: " + e.getMessage());
            return false;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    public static boolean tableExists(Connection conn, String tableName) {
        String sql = "SELECT 1 FROM information_schema.tables WHERE table_name = '" + tableName + "'";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking if table exists: " + e.getMessage());
            return false;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    public static boolean createDatabase(Connection conn, String dbName) {
        String sql = "CREATE DATABASE " + dbName;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Database '" + dbName + "' created successfully");
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
            return false;
        } finally {
            closeStatement(stmt);
        }
    }

    public static List<String> loadSQLScript(String resourcePath) {
        try (InputStream is = DBUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder script = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                    continue;
                }
                script.append(line).append(" ");
            }

            String[] statements = script.toString().split(";");
            List<String> result = new ArrayList<>();

            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty() && !trimmed.startsWith("\\c")) {
                    result.add(trimmed + ";");
                }
            }

            return result;
        } catch (IOException e) {
            System.err.println("Error loading SQL script: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void executeStatements(Connection conn, List<String> statements) {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            for (String sql : statements) {
                if (!sql.trim().isEmpty() && !sql.trim().startsWith("--") && !sql.trim().startsWith("\\c")) {
                    try {
                        stmt.executeUpdate(sql);
                    } catch (SQLException e) {
                        System.err.println("Error executing SQL: " + e.getMessage());
                        System.err.println("Statement: " + sql);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating statement: " + e.getMessage());
        } finally {
            closeStatement(stmt);
        }
    }

    public static void printSQLException(SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
        System.err.println("SQLState: " + e.getSQLState());
        System.err.println("VendorError: " + e.getErrorCode());
    }
}