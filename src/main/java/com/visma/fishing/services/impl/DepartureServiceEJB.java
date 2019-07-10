package com.visma.fishing.services.impl;

import com.visma.fishing.services.DepartureService;
import com.visma.fishing.model.Departure;
import com.visma.fishing.model.Logbook;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Transactional
@Stateless
public class DepartureServiceEJB implements DepartureService {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Departure> findAll() {
        TypedQuery<Departure> q = em.createQuery("SELECT a FROM Departure a", Departure.class);
        return q.getResultList();
    }

    @Override
    public Optional<Departure> findById(String id) {
        return Optional.ofNullable(em.find(Departure.class, id));
    }

    @Override
    public Response create(Departure departure) {
        em.persist(departure);
        return Response.ok("Successfully saved departure to file system.").build();

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
