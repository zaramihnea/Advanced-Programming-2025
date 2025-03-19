package com.example.droneexplorer.model;

public class Connection {
    private Location source;
    private Location destination;
    private double travelTime;
    private double safetyProbability;

    public Connection(Location source, Location destination, double travelTime, double safetyProbability) {
        this.source = source;
        this.destination = destination;
        this.travelTime = travelTime;
        this.safetyProbability = safetyProbability;
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public double getSafetyProbability() {
        return safetyProbability;
    }

    @Override
    public String toString() {
        return source.getName() + " -> " + destination.getName() +
                " (Time: " + travelTime + ", Safety: " + safetyProbability + ")";
    }
}