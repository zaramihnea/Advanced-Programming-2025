package com.serverless.dao;

import com.serverless.model.Country;
import java.util.List;

public interface CountryDAO {
    void create(Country country);

    Country findById(int id);

    Country findByName(String name);

    List<Country> findByContinent(int continentId);

    List<Country> findAll();
}