package com.serverless.dao;

import com.serverless.db.EntityManagerFactorySingleton;
import com.serverless.model.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CityRepository {
    private final EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

    public void create(City city) {
        em.getTransaction().begin();
        em.persist(city);
        em.getTransaction().commit();
    }

    public City findById(int id) {
        return em.find(City.class, id);
    }

    public List<City> findByName(String namePattern) {
        TypedQuery<City> query = em.createNamedQuery("City.findByName", City.class);
        query.setParameter("name", namePattern);
        return query.getResultList();
    }

    public List<City> findAll() {
        return em.createQuery("SELECT c FROM City c", City.class).getResultList();
    }

    public List<City> findAllCapitals() {
        return em.createQuery("SELECT c FROM City c WHERE c.capital = true", City.class).getResultList();
    }
} 