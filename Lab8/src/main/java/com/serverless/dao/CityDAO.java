package com.serverless.dao;

import com.serverless.model.City;
import java.util.List;

public interface CityDAO {
    void create(City city);

    City findById(int id);

    City findByNameAndCountry(String name, int countryId);

    List<City> findByCountry(int countryId);

    List<City> findAllCapitals();

    List<City> findAll();

    int batchInsert(List<City> cities);
}