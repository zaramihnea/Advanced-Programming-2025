package com.example.droneexplorer.util;

import com.example.droneexplorer.model.Connection;
import com.example.droneexplorer.model.Location;
import com.example.droneexplorer.model.DroneMap;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.HashMap;
import java.util.List;
import java.util.Collections;

public class PathFinder {
    private DroneMap map;
    private Graph<Location, DefaultWeightedEdge> timeGraph;

    public PathFinder(DroneMap map) {
        this.map = map;
        initializeGraph();
    }

    private void initializeGraph() {
        timeGraph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
        for (Location location : map.getLocations()) {
            timeGraph.addVertex(location);
        }
        for (Connection connection : map.getConnections()) {
            Location source = connection.getSource();
            Location destination = connection.getDestination();
            DefaultWeightedEdge timeEdge = timeGraph.addEdge(source, destination);
            if (timeEdge != null) {
                timeGraph.setEdgeWeight(timeEdge, connection.getTravelTime());
            }
        }
    }

    public java.util.Map<Location, Double> findFastestRoutes() {
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra =
                new DijkstraShortestPath<>(timeGraph);

        java.util.Map<Location, Double> fastestTimes = new HashMap<>();
        Location startLocation = map.getStartLocation();

        for (Location destination : map.getLocations()) {
            if (destination.equals(startLocation)) {
                continue;
            }

            GraphPath<Location, DefaultWeightedEdge> path = dijkstra.getPath(startLocation, destination);
            if (path != null) {
                fastestTimes.put(destination, path.getWeight());
            } else {
                fastestTimes.put(destination, Double.POSITIVE_INFINITY);
            }
        }

        return fastestTimes;
    }

    public List<Location> getFastestPath(Location destination) {
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra =
                new DijkstraShortestPath<>(timeGraph);

        GraphPath<Location, DefaultWeightedEdge> path =
                dijkstra.getPath(map.getStartLocation(), destination);

        return path != null ? path.getVertexList() : Collections.emptyList();
    }
}