package com.serverless.util;

import com.serverless.model.City;

public class DistanceCalculator {
    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * Calculate the distance between two cities using their coordinates.
     * @param city1 First city
     * @param city2 Second city
     * @return Distance in kilometers
     */
    public static double calculateDistance(City city1, City city2) {
        return calculateDistance(
                city1.getLatitude(), city1.getLongitude(),
                city2.getLatitude(), city2.getLongitude()
        );
    }

    /**
     * Calculate the distance between two points on Earth using the Haversine formula.
     * @param lat1 Latitude of first point in degrees
     * @param lon1 Longitude of first point in degrees
     * @param lat2 Latitude of second point in degrees
     * @param lon2 Longitude of second point in degrees
     * @return Distance in kilometers
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    /**
     * Format distance in a human-readable way.
     * @param distance Distance in kilometers
     * @return Formatted distance string
     */
    public static String formatDistance(double distance) {
        if (distance < 1) {
            return String.format("%.0f meters", distance * 1000);
        } else if (distance < 10) {
            return String.format("%.1f km", distance);
        } else {
            return String.format("%.0f km", distance);
        }
    }
}