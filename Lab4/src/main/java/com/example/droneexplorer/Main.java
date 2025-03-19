package com.example.droneexplorer;

import com.example.droneexplorer.model.*;
import com.example.droneexplorer.util.PathFinder;
import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final int NUM_LOCATIONS = 15;

    public static void main(String[] args) {
        Location[] locationList = generateRandomLocations();
        DroneMap droneMap = buildMapWithConnections(locationList);

        Random rand = new Random();
        Location start = locationList[rand.nextInt(locationList.length)];
        droneMap.setStartLocation(start);
        System.out.println("Starting at: " + start.getName() + " (" + start.getType() + ")");

        // COMPULSORY STUFF
        TreeSet<Location> friendlyLocs = Arrays.stream(locationList)
                .filter(l -> l.getType() == LocationType.FRIENDLY)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println("\n-- Friendly Locations (sorted naturally) --");
        friendlyLocs.forEach(System.out::println);

        LinkedList<Location> enemyLocs = Arrays.stream(locationList)
                .filter(l -> l.getType() == LocationType.ENEMY)
                .collect(Collectors.toCollection(LinkedList::new));

        enemyLocs.sort(Comparator.comparing(Location::getType)
                .thenComparing(Location::getName));

        System.out.println("\n-- Enemy Locations (sorted by type & name) --");
        enemyLocs.forEach(System.out::println);

        // HOMEWORK STUFF
        Map<LocationType, List<Location>> typeGroups = Arrays.stream(locationList)
                .collect(Collectors.groupingBy(Location::getType));

        System.out.println("\n-- Locations By Type --");
        typeGroups.forEach((type, locs) -> {
            System.out.println(type + ":");
            locs.forEach(l -> System.out.println("  " + l.getName()));
        });

        System.out.println("\n-- Finding Fastest Routes --");
        PathFinder routeFinder = new PathFinder(droneMap);
        Map<Location, Double> routeTimes = routeFinder.findFastestRoutes();

        System.out.println("\nFastest routes to FRIENDLY locations:");
        routeTimes.entrySet().stream()
                .filter(e -> e.getKey().getType() == LocationType.FRIENDLY)
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> {
                    // Print with nice formatting
                    System.out.printf("  To %s: %.1f time units%n",
                            e.getKey().getName(), e.getValue());
                });

        System.out.println("\nFastest routes to NEUTRAL locations:");
        routeTimes.entrySet().stream()
                .filter(e -> e.getKey().getType() == LocationType.NEUTRAL)
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> {
                    System.out.printf("  To %s: %.1f time units%n",
                            e.getKey().getName(), e.getValue());
                });

        System.out.println("\nFastest routes to ENEMY locations:");
        routeTimes.entrySet().stream()
                .filter(e -> e.getKey().getType() == LocationType.ENEMY)
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> {
                    System.out.printf("  To %s: %.1f time units%n",
                            e.getKey().getName(), e.getValue());
                });

        System.out.println("\nDone!");
    }

    private static Location[] generateRandomLocations() {
        Faker faker = new Faker();
        Location[] locs = new Location[NUM_LOCATIONS];

        for (int i = 0; i < NUM_LOCATIONS; i++) {
            String name = faker.address().cityName();
            int typeIdx = faker.random().nextInt(LocationType.values().length);
            LocationType type = LocationType.values()[typeIdx];
            locs[i] = new Location(name, type);
        }

        return locs;
    }

    private static DroneMap buildMapWithConnections(Location[] locs) {
        DroneMap map = new DroneMap();
        Random r = new Random();

        for (Location loc : locs) {
            map.addLocation(loc);
        }

        for (Location from : locs) {
            int numConnections = 1 + r.nextInt(3);

            for (int i = 0; i < numConnections; i++) {
                Location to = locs[r.nextInt(locs.length)];

                if (!from.equals(to)) {
                    double time = 1.0 + r.nextDouble() * 10.0;
                    double safety = r.nextDouble();

                    map.addConnection(from, to, time, safety);
                }
            }
        }

        return map;
    }
}