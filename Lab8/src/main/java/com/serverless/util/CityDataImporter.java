package com.serverless.util;

import com.serverless.dao.CountryDAO;
import com.serverless.dao.impl.CountryDAOImpl;
import com.serverless.model.City;
import com.serverless.model.Country;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityDataImporter {
    private final CountryDAO countryDAO;

    public CityDataImporter() {
        this.countryDAO = new CountryDAOImpl();
    }

    public List<City> importFromCsv(
            String csvFilePath,
            boolean hasHeader,
            int cityNameIndex,
            int countryNameIndex,
            int countryCodeIndex,
            int isCapitalIndex,
            int latitudeIndex,
            int longitudeIndex) {

        List<City> cities = new ArrayList<>();
        Map<String, Integer> countryCache = new HashMap<>();

        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, hasHeader ?
                     CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build() :
                     CSVFormat.DEFAULT)) {

            List<Country> existingCountries = countryDAO.findAll();
            for (Country country : existingCountries) {
                countryCache.put(country.getCode(), country.getId());
            }

            for (CSVRecord record : csvParser) {
                try {
                    String cityName = record.get(cityNameIndex).trim();
                    String countryName = record.get(countryNameIndex).trim();
                    String countryCode = record.get(countryCodeIndex).trim().toUpperCase();

                    boolean isCapital = isCapitalIndex >= 0 &&
                            ("1".equals(record.get(isCapitalIndex).trim()) ||
                                    "true".equalsIgnoreCase(record.get(isCapitalIndex).trim()) ||
                                    "yes".equalsIgnoreCase(record.get(isCapitalIndex).trim()));

                    double latitude = Double.parseDouble(record.get(latitudeIndex).trim());
                    double longitude = Double.parseDouble(record.get(longitudeIndex).trim());

                    if (cityName.isEmpty()) {
                        continue;
                    }

                    Integer countryId = countryCache.get(countryCode);

                    if (countryId == null) {
                        Country country = countryDAO.findByName(countryName);

                        if (country == null) {
                            country = new Country(countryName, countryCode, 3);
                            countryDAO.create(country);
                        }

                        countryId = country.getId();
                        countryCache.put(countryCode, countryId);
                    }

                    City city = new City(cityName, countryId, isCapital, latitude, longitude);
                    cities.add(city);

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error parsing record: " + e.getMessage());
                }
            }

            System.out.println("Imported " + cities.size() + " cities from CSV");

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        return cities;
    }
}