package com.visma.fishing.services.impl;

import com.visma.fishing.services.ArrivalService;
import com.visma.fishing.model.Arrival;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Stateless
public class ArrivalServiceEJB implements ArrivalService {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Arrival> findAll() {
        TypedQuery<Arrival> q = em.createQuery("SELECT a FROM Arrival a", Arrival.class);
        return q.getResultList();
    }

    @Override
    public Optional<Arrival> findById(Long id) {
        return Optional.ofNullable(em.find(Arrival.class, id));
    }

    @Override
    public Response create(Arrival arrival) {
        em.persist(arrival);
        return Response.ok("Successfully saved arrival to database.").build();

    }

    @Override
    public Response update(Long id, Arrival arrival) {
        Arrival entity = em.find(Arrival.class, id);
        entity.setPort(arrival.getPort());
        em.merge(entity);
        return Response.ok("Successfully updated arrival.").build();

    }

    @Override
    public void remove(Long id) {
        Arrival entity = em.find(Arrival.class, id);
        em.remove(entity);
    }


}
