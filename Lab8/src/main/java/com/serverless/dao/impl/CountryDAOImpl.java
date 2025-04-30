package com.serverless.dao.impl;

import com.serverless.config.DatabaseConfig;
import com.serverless.dao.CountryDAO;
import com.serverless.db.DatabaseConnection;
import com.serverless.model.Country;
import com.serverless.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAOImpl implements CountryDAO {
    private final Connection connection;

    public CountryDAOImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void create(Country country) {
        String sql = "INSERT INTO " + DatabaseConfig.COUNTRY_TABLE +
                " (name, code, continent_id) VALUES (?, ?, ?) RETURNING id";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getCode());
            stmt.setInt(3, country.getContinentId());
            rs = stmt.executeQuery();

            if (rs.next()) {
                country.setId(rs.getInt(1));
            }
            System.out.println("Created country: " + country);
        } catch (SQLException e) {
            System.err.println("Error creating country: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }
    }

    @Override
    public Country findById(int id) {
        String sql = "SELECT * FROM " + DatabaseConfig.COUNTRY_TABLE + " WHERE id = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new Country(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("continent_id")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding country by ID: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }

        return null;
    }

    @Override
    public Country findByName(String name) {
        String sql = "SELECT * FROM " + DatabaseConfig.COUNTRY_TABLE + " WHERE name = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new Country(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("continent_id")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding country by name: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }

        return null;
    }

    @Override
    public List<Country> findByContinent(int continentId) {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT * FROM " + DatabaseConfig.COUNTRY_TABLE + " WHERE continent_id = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, continentId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                countries.add(new Country(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("continent_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error finding countries by continent: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }

        return countries;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT * FROM " + DatabaseConfig.COUNTRY_TABLE;

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                countries.add(new Country(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("continent_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all countries: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
        }

        return countries;
    }
}