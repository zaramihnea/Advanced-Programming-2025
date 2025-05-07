package com.serverless;

import com.serverless.dao.ContinentRepository;
import com.serverless.dao.CountryRepository;
import com.serverless.dao.CityRepository;
import com.serverless.model.Continent;
import com.serverless.model.Country;
import com.serverless.model.City;
import com.serverless.db.EntityManagerFactorySingleton;
import com.serverless.util.DistanceCalculator;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContinentRepository continentRepo = new ContinentRepository();
        CountryRepository countryRepo = new CountryRepository();
        CityRepository cityRepo = new CityRepository();
        EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
        try {
            boolean exit = false;
            while (!exit) {
                System.out.println("\n--- JPA WORLD CITIES ---");
                System.out.println("1. Add continent");
                System.out.println("2. Add country");
                System.out.println("3. Add city");
                System.out.println("4. List all continents");
                System.out.println("5. List all countries");
                System.out.println("6. List all cities");
                System.out.println("7. Find continent by name pattern");
                System.out.println("8. Find country by name pattern");
                System.out.println("9. Find city by name pattern");
                System.out.println("10. Calculate distance between cities");
                System.out.println("0. Exit");
                System.out.print("Choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 0 -> exit = true;
                    case 1 -> {
                        System.out.print("Continent name: ");
                        String name = scanner.nextLine();
                        continentRepo.create(new Continent(name));
                        System.out.println("Continent added.");
                    }
                    case 2 -> {
                        System.out.print("Country name: ");
                        String cname = scanner.nextLine();
                        System.out.print("Country code: ");
                        String ccode = scanner.nextLine();
                        System.out.print("Continent id: ");
                        int contId = Integer.parseInt(scanner.nextLine());
                        Continent cont = continentRepo.findById(contId);
                        if (cont == null) {
                            System.out.println("Continent not found!");
                        } else {
                            countryRepo.create(new Country(cname, ccode, cont));
                            System.out.println("Country added.");
                        }
                    }
                    case 3 -> {
                        System.out.print("City name: ");
                        String cityName = scanner.nextLine();
                        System.out.print("Country id: ");
                        int countryId = Integer.parseInt(scanner.nextLine());
                        Country country = countryRepo.findById(countryId);
                        if (country == null) {
                            System.out.println("Country not found!");
                            break;
                        }
                        System.out.print("Is capital? (y/n): ");
                        boolean isCapital = scanner.nextLine().trim().toLowerCase().startsWith("y");
                        System.out.print("Latitude: ");
                        double lat = Double.parseDouble(scanner.nextLine());
                        System.out.print("Longitude: ");
                        double lon = Double.parseDouble(scanner.nextLine());
                        cityRepo.create(new City(cityName, country, isCapital, lat, lon));
                        System.out.println("City added.");
                    }
                    case 4 -> continentRepo.findAll().forEach(System.out::println);
                    case 5 -> countryRepo.findAll().forEach(System.out::println);
                    case 6 -> cityRepo.findAll().forEach(System.out::println);
                    case 7 -> {
                        System.out.print("Pattern (ex: %a%): ");
                        String pattern = scanner.nextLine();
                        continentRepo.findByName(pattern).forEach(System.out::println);
                    }
                    case 8 -> {
                        System.out.print("Pattern (ex: %a%): ");
                        String pattern = scanner.nextLine();
                        countryRepo.findByName(pattern).forEach(System.out::println);
                    }
                    case 9 -> {
                        System.out.print("Pattern (ex: %a%): ");
                        String pattern = scanner.nextLine();
                        cityRepo.findByName(pattern).forEach(System.out::println);
                    }
                    case 10 -> calculateCityDistances(cityRepo, scanner);
                    default -> System.out.println("Invalid!");
                }
            }
        } finally {
            em.close();
            EntityManagerFactorySingleton.close();
            scanner.close();
        }
    }
    
    private static void calculateCityDistances(CityRepository cityRepo, Scanner scanner) {
        System.out.println("\n===== CITY DISTANCES =====");
        
        System.out.println("1. Distance between two cities");
        System.out.println("2. Distances from one city to multiple cities");
        System.out.print("Choose option: ");
        int option = Integer.parseInt(scanner.nextLine().trim());
        
        if (option == 1) {
            System.out.print("Enter first city ID: ");
            int city1Id = Integer.parseInt(scanner.nextLine().trim());
            City city1 = cityRepo.findById(city1Id);
            
            if (city1 == null) {
                System.out.println("City not found.");
                return;
            }
            
            System.out.print("Enter second city ID: ");
            int city2Id = Integer.parseInt(scanner.nextLine().trim());
            City city2 = cityRepo.findById(city2Id);
            
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
            City source = cityRepo.findById(sourceId);
            
            if (source == null) {
                System.out.println("City not found.");
                return;
            }
            
            System.out.println("Target cities:");
            System.out.println("1. All cities");
            System.out.println("2. Capital cities only");
            System.out.print("Choose option: ");
            int targetOption = Integer.parseInt(scanner.nextLine().trim());
            
            List<City> targetCities;
            
            if (targetOption == 1) {
                targetCities = cityRepo.findAll();
            } else if (targetOption == 2) {
                targetCities = cityRepo.findAllCapitals();
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