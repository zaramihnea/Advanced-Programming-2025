package com.serverless.model;

public class City {
    private int id;
    private String name;
    private int countryId;
    private boolean capital;
    private double latitude;
    private double longitude;

    public City() {
    }

    public City(String name, int countryId, boolean capital, double latitude, double longitude) {
        this.name = name;
        this.countryId = countryId;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City(int id, String name, int countryId, boolean capital, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public boolean isCapital() {
        return capital;
    }

    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId=" + countryId +
                ", capital=" + capital +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}