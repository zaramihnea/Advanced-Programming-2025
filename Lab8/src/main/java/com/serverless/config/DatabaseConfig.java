package com.serverless.config;

public class DatabaseConfig {
    public static final String HOST = "localhost";
    public static final String PORT = "5433";
    public static final String DEFAULT_DB = "postgres";
    public static final String APP_DB = "world_cities";

    public static final String DEFAULT_URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DEFAULT_DB;
    public static final String APP_URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + APP_DB;
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres"; // Change to your password

    public static final String CONTINENT_TABLE = "continents";
    public static final String COUNTRY_TABLE = "countries";

    private DatabaseConfig() {
        throw new IllegalStateException("Configuration class");
    }
}