package com.serverless.dao;

import com.serverless.db.EntityManagerFactorySingleton;
import com.serverless.model.Continent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ContinentRepository {
    private final EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

    public void create(Continent continent) {
        em.getTransaction().begin();
        em.persist(continent);
        em.getTransaction().commit();
    }

    public Continent findById(int id) {
        return em.find(Continent.class, id);
    }

    public List<Continent> findByName(String namePattern) {
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findByName", Continent.class);
        query.setParameter("name", namePattern);
        return query.getResultList();
    }

    public List<Continent> findAll() {
        return em.createQuery("SELECT c FROM Continent c", Continent.class).getResultList();
    }
} 