package com.serverless.dao;

import com.serverless.model.Continent;
import java.util.List;

public interface ContinentDAO {

    void create(Continent continent);

    Continent findById(int id);

    Continent findByName(String name);

    List<Continent> findAll();
}