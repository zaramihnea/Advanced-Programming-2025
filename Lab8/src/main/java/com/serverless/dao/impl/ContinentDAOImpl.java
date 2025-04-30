package com.serverless.dao.impl;

import com.serverless.config.DatabaseConfig;
import com.serverless.dao.ContinentDAO;
import com.serverless.db.DatabaseConnection;
import com.serverless.model.Continent;
import com.serverless.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContinentDAOImpl implements ContinentDAO {
    private final Connection connection;

    public ContinentDAOImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void create(Continent continent) {
        String sql = "INSERT INTO " + DatabaseConfig.CONTINENT_TABLE + " (name) VALUES (?) RETURNING id";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, continent.getName());
            rs = stmt.executeQuery();

            if (rs.next()) {
                continent.setId(rs.getInt(1));
            }
            System.out.println("Created continent: " + continent);
        } catch (SQLException e) {
            System.err.println("Error creating continent: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }
    }

    @Override
    public Continent findById(int id) {
        String sql = "SELECT * FROM " + DatabaseConfig.CONTINENT_TABLE + " WHERE id = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new Continent(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding continent by ID: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }

        return null;
    }

    @Override
    public Continent findByName(String name) {
        String sql = "SELECT * FROM " + DatabaseConfig.CONTINENT_TABLE + " WHERE name = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new Continent(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding continent by name: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }

        return null;
    }

    @Override
    public List<Continent> findAll() {
        List<Continent> continents = new ArrayList<>();
        String sql = "SELECT * FROM " + DatabaseConfig.CONTINENT_TABLE;

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                continents.add(new Continent(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all continents: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }

        return continents;
    }
}