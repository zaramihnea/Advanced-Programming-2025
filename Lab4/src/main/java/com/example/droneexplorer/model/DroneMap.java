package com.example.droneexplorer.model;

import java.util.*;

public class DroneMap {
    private Set<Location> locations;
    private Set<Connection> connections;
    private Location startLocation;

    public DroneMap() {
        this.locations = new HashSet<>();
        this.connections = new HashSet<>();
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public void setStartLocation(Location startLocation) {
        locations.add(startLocation);
        this.startLocation = startLocation;
    }

    public void addConnection(Location source, Location destination, double time, double safety) {
        connections.add(new Connection(source, destination, time, safety));
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Set<Connection> getConnections() {
        return connections;
    }

    public Location getStartLocation() {
        return startLocation;
    }
}