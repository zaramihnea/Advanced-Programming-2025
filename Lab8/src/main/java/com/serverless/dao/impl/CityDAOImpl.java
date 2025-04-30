package com.serverless.dao.impl;

import com.serverless.dao.CityDAO;
import com.serverless.db.DatabaseConnection;
import com.serverless.model.City;
import com.serverless.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAOImpl implements CityDAO {

    @Override
    public void create(City city) {
        String sql = "INSERT INTO cities (name, country_id, capital, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, city.getName());
            stmt.setInt(2, city.getCountryId());
            stmt.setBoolean(3, city.isCapital());
            stmt.setDouble(4, city.getLatitude());
            stmt.setDouble(5, city.getLongitude());
            rs = stmt.executeQuery();

            if (rs.next()) {
                city.setId(rs.getInt(1));
            }
            System.out.println("Created city: " + city);
        } catch (SQLException e) {
            System.err.println("Error creating city: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(conn);
        }
    }

    @Override
    public City findById(int id) {
        String sql = "SELECT * FROM cities WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return extractCityFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding city by ID: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(conn);
        }

        return null;
    }

    @Override
    public City findByNameAndCountry(String name, int countryId) {
        String sql = "SELECT * FROM cities WHERE name = ? AND country_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, countryId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return extractCityFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding city by name and country: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(conn);
        }

        return null;
    }

    @Override
    public List<City> findByCountry(int countryId) {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM cities WHERE country_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, countryId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cities.add(extractCityFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding cities by country: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(conn);
        }

        return cities;
    }

    @Override
    public List<City> findAllCapitals() {
        List<City> capitals = new ArrayList<>();
        String sql = "SELECT * FROM cities WHERE capital = true";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                capitals.add(extractCityFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all capitals: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(conn);
        }

        return capitals;
    }

    @Override
    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM cities";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                cities.add(extractCityFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all cities: " + e.getMessage());
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(conn);
        }

        return cities;
    }

    @Override
    public int batchInsert(List<City> cities) {
        String sql = "INSERT INTO cities (name, country_id, capital, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        int count = 0;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);

            for (City city : cities) {
                stmt.setString(1, city.getName());
                stmt.setInt(2, city.getCountryId());
                stmt.setBoolean(3, city.isCapital());
                stmt.setDouble(4, city.getLatitude());
                stmt.setDouble(5, city.getLongitude());
                stmt.addBatch();
                count++;
                if (count % 1000 == 0) {
                    stmt.executeBatch();
                }
            }
            stmt.executeBatch();
            conn.commit();

            System.out.println("Batch inserted " + count + " cities");
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.err.println("Error in batch insert of cities: " + e.getMessage());
            count = 0;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(conn);
        }

        return count;
    }
    private City extractCityFromResultSet(ResultSet rs) throws SQLException {
        return new City(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("country_id"),
                rs.getBoolean("capital"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude")
        );
    }
}