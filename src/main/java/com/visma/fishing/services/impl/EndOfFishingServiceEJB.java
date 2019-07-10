package com.visma.fishing.services.impl;

import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.services.EndOfFishingService;

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
public class EndOfFishingServiceEJB implements EndOfFishingService {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<EndOfFishing> findAll() {
        TypedQuery<EndOfFishing> q = em.createQuery("SELECT a FROM EndOfFishing a", EndOfFishing.class);
        return q.getResultList();
    }

    @Override
    public Optional<EndOfFishing> findById(String id) {
        return Optional.ofNullable(em.find(EndOfFishing.class, id));
    }

    @Override
    public Response create(EndOfFishing endOfFishing) {
        em.persist(endOfFishing);
        return Response.ok("Successfully saved endOfFishing to database.").build();
    }

    @Override
    public void update(Long id, EndOfFishing endOfFishing) {
        EndOfFishing entity = em.find(EndOfFishing.class, id);
        entity.setDate(endOfFishing.getDate());
    }

    @Override
    public void remove(Long id) {
        EndOfFishing entity = em.find(EndOfFishing.class, id);
        em.remove(entity);
    }
}
