package com.visma.fishing.EJB.impl;

import com.visma.fishing.EJB.DepartureEJB;
import com.visma.fishing.model.Departure;
import com.visma.fishing.model.Logbook;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class DepartureEJBImpl implements DepartureEJB {

    @PersistenceContext
    EntityManager em;


    @Override
    public List<Departure> findAll() {
        Query q = em.createQuery("SELECT a FROM Departure a");
        return q.getResultList();
    }

    @Override
    public Departure findById(Long id) {
        return em.find(Departure.class, id);
    }

    @Override
    public void create(Departure departure) {
        em.persist(departure);
    }

    @Override
    public void update(Long id, Departure departure) {
        Departure entity = em.find(Departure.class, id);
        entity.setDate(departure.getDate());
        entity.setPort(departure.getPort());
        em.merge(entity);
    }

    @Override
    public void remove(Long id) {
        Logbook entity = em.find(Logbook.class, id);
        em.remove(entity);
    }
}
