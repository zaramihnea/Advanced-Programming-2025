package com.serverless;

import com.serverless.dao.CityDAO;
import com.serverless.dao.ContinentDAO;
import com.serverless.dao.CountryDAO;
import com.serverless.dao.impl.CityDAOImpl;
import com.serverless.dao.impl.ContinentDAOImpl;
import com.serverless.dao.impl.CountryDAOImpl;
import com.serverless.db.DatabaseConnection;
import com.serverless.model.City;
import com.serverless.model.Continent;
import com.serverless.model.Country;
import com.serverless.util.CityDataImporter;
import com.serverless.util.DistanceCalculator;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Starting World Cities JDBC application...");

            ContinentDAO continentDAO = new ContinentDAOImpl();
            CountryDAO countryDAO = new CountryDAOImpl();
            CityDAO cityDAO = new CityDAOImpl();

            Scanner scanner = new Scanner(System.in);

            boolean exit = false;
            while (!exit) {
                System.out.println("\n--- WORLD CITIES DATABASE ---");
                System.out.println("1. Run basic tests (continents & countries)");
                System.out.println("2. Import city data from CSV");
                System.out.println("3. Add a new city");
                System.out.println("4. List all capital cities");
                System.out.println("5. Calculate distance between cities");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                int choice = -1;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 0:
                        exit = true;
                        break;

                    case 1:
                        testContinents(continentDAO);
                        testCountries(countryDAO, continentDAO);
                        break;

                    case 2:
                        importCityData(cityDAO);
                        break;

                    case 3:
                        addNewCity(cityDAO, countryDAO);
                        break;

                    case 4:
                        listCapitalCities(cityDAO);
                        break;

                    case 5:
                        calculateCityDistances(cityDAO);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            scanner.close();

        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }
    }
    private static void testContinents(ContinentDAO continentDAO) {
        System.out.println("===== TESTING CONTINENTS =====");

        System.out.println("\nListing all continents...");
        List<Continent> allContinents = continentDAO.findAll();
        for (Continent c : allContinents) {
            System.out.println(c);
        }

        if (allContinents.isEmpty()) {
            System.out.println("\nCreating new continent...");
            Continent newContinent = new Continent("Test Continent");
            continentDAO.create(newContinent);
            System.out.println("Created: " + newContinent);
        }
    }

    private static void testCountries(CountryDAO countryDAO, ContinentDAO continentDAO) {
        System.out.println("\n===== TESTING COUNTRIES =====");

        System.out.println("\nListing all countries...");
        List<Country> allCountries = countryDAO.findAll();
        for (Country c : allCountries) {
            System.out.println(c);
        }

        Continent europeContinent = continentDAO.findByName("Europe");
        if (europeContinent == null) {
            System.out.println("Europe continent not found. Creating it...");
            europeContinent = new Continent("Europe");
            continentDAO.create(europeContinent);
        }

        if (allCountries.isEmpty()) {
            System.out.println("\nCreating new country...");
            Country newCountry = new Country("Test Country", "TST", europeContinent.getId());
            countryDAO.create(newCountry);
            System.out.println("Created: " + newCountry);
        }
    }

    private static void importCityData(CityDAO cityDAO) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== IMPORT CITY DATA =====");
        System.out.print("Enter CSV file path: ");
        String filePath = scanner.nextLine();

        System.out.println("CSV Column Configuration");
        System.out.print("Has header row? (y/n): ");
        boolean hasHeader = scanner.nextLine().trim().toLowerCase().startsWith("y");

        System.out.print("City name column index: ");
        int cityNameIdx = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Country name column index: ");
        int countryNameIdx = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Country code column index: ");
        int countryCodeIdx = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Is capital column index (-1 if not present): ");
        int isCapitalIdx = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Latitude column index: ");
        int latIdx = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Longitude column index: ");
        int lonIdx = Integer.parseInt(scanner.nextLine().trim());

        CityDataImporter importer = new CityDataImporter();
        List<City> cities = importer.importFromCsv(
                filePath, hasHeader, cityNameIdx, countryNameIdx, countryCodeIdx,
                isCapitalIdx, latIdx, lonIdx
        );

        if (!cities.isEmpty()) {
            System.out.print("Import " + cities.size() + " cities? (y/n): ");
            if (scanner.nextLine().trim().toLowerCase().startsWith("y")) {
                int imported = cityDAO.batchInsert(cities);
                System.out.println("Successfully imported " + imported + " cities.");
            } else {
                System.out.println("Import cancelled.");
            }
        } else {
            System.out.println("No cities found to import.");
        }
    }

    private static void addNewCity(CityDAO cityDAO, CountryDAO countryDAO) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== ADD NEW CITY =====");

        System.out.print("City name: ");
        String name = scanner.nextLine().trim();

        List<Country> countries = countryDAO.findAll();
        System.out.println("\nAvailable countries:");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println((i + 1) + ": " + countries.get(i).getName() +
                    " (" + countries.get(i).getCode() + ")");
        }

        System.out.print("Select country (number): ");
        int countryIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;

        if (countryIdx < 0 || countryIdx >= countries.size()) {
            System.out.println("Invalid country selection.");
            return;
        }

        Country selectedCountry = countries.get(countryIdx);

        System.out.print("Is this a capital city? (y/n): ");
        boolean isCapital = scanner.nextLine().trim().toLowerCase().startsWith("y");

        System.out.print("Latitude: ");
        double latitude = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Longitude: ");
        double longitude = Double.parseDouble(scanner.nextLine().trim());

        City city = new City(name, selectedCountry.getId(), isCapital, latitude, longitude);
        cityDAO.create(city);

        System.out.println("City added successfully: " + city);
    }

    private static void listCapitalCities(CityDAO cityDAO) {
        System.out.println("\n===== CAPITAL CITIES =====");

        List<City> capitals = cityDAO.findAllCapitals();

        if (capitals.isEmpty()) {
            System.out.println("No capital cities found.");
            return;
        }

        for (City capital : capitals) {
            System.out.println(capital);
        }

        System.out.println("Total: " + capitals.size() + " capital cities");
    }

    private static void calculateCityDistances(CityDAO cityDAO) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== CITY DISTANCES =====");

        System.out.println("1. Distance between two cities");
        System.out.println("2. Distances from one city to multiple cities");
        System.out.print("Choose option: ");
        int option = Integer.parseInt(scanner.nextLine().trim());

        if (option == 1) {
            System.out.print("Enter first city ID: ");
            int city1Id = Integer.parseInt(scanner.nextLine().trim());
            City city1 = cityDAO.findById(city1Id);

            if (city1 == null) {
                System.out.println("City not found.");
                return;
            }

            System.out.print("Enter second city ID: ");
            int city2Id = Integer.parseInt(scanner.nextLine().trim());
            City city2 = cityDAO.findById(city2Id);

            if (city2 == null) {
                System.out.println("City not found.");
                return;
            }

            double distance = DistanceCalculator.calculateDistance(city1, city2);

            System.out.println("Distance between " + city1.getName() + " and " +
                    city2.getName() + ": " +
                    DistanceCalculator.formatDistance(distance));

        } else if (option == 2) {
            System.out.print("Enter source city ID: ");
            int sourceId = Integer.parseInt(scanner.nextLine().trim());
            City source = cityDAO.findById(sourceId);

            if (source == null) {
                System.out.println("City not found.");
                return;
            }

            System.out.println("Target cities:");
            System.out.println("1. All capital cities");
            System.out.println("2. Cities in a specific country");
            System.out.print("Choose option: ");
            int targetOption = Integer.parseInt(scanner.nextLine().trim());

            List<City> targetCities;

            if (targetOption == 1) {
                targetCities = cityDAO.findAllCapitals();
            } else if (targetOption == 2) {
                System.out.print("Enter country ID: ");
                int countryId = Integer.parseInt(scanner.nextLine().trim());
                targetCities = cityDAO.findByCountry(countryId);
            } else {
                System.out.println("Invalid option.");
                return;
            }

            if (targetCities.isEmpty()) {
                System.out.println("No target cities found.");
                return;
            }

            System.out.println("\nDistances from " + source.getName() + ":");
            for (City target : targetCities) {
                if (target.getId() == source.getId()) {
                    continue;
                }

                double distance = DistanceCalculator.calculateDistance(source, target);
                System.out.println(target.getName() + ": " +
                        DistanceCalculator.formatDistance(distance));
            }
        } else {
            System.out.println("Invalid option.");
        }
    }
}