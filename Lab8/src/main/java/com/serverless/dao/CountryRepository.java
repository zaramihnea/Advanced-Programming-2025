package com.serverless.dao;

import com.serverless.db.EntityManagerFactorySingleton;
import com.serverless.model.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CountryRepository {
    private final EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

    public void create(Country country) {
        em.getTransaction().begin();
        em.persist(country);
        em.getTransaction().commit();
    }

    public Country findById(int id) {
        return em.find(Country.class, id);
    }

    public List<Country> findByName(String namePattern) {
        TypedQuery<Country> query = em.createNamedQuery("Country.findByName", Country.class);
        query.setParameter("name", namePattern);
        return query.getResultList();
    }

    public List<Country> findAll() {
        return em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
    }
} 