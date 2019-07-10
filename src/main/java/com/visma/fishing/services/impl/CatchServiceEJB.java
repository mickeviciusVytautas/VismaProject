package com.visma.fishing.services.impl;

import com.visma.fishing.services.CatchService;
import com.visma.fishing.model.Catch;

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
public class CatchServiceEJB implements CatchService {

    @PersistenceContext(name = "prod")
    EntityManager em;

    @Override
    public List<Catch> findAll() {
        TypedQuery<Catch> q = em.createQuery("SELECT a FROM Catch a", Catch.class);
        return q.getResultList();

    }

    @Override
    public Optional<Catch> findById(String id) {
        return Optional.ofNullable(em.find(Catch.class, id));
    }

    @Override
    public Response create(Catch aCatch) {
        em.persist(aCatch);
        return Response.ok("Successfully saved endOfFishing to database.").build();
    }

    @Override
    public void update(Long id, Catch aCatch) {
        Catch entity = em.find(Catch.class, id);
        entity.setSpecies(aCatch.getSpecies());
        entity.setWeight(aCatch.getWeight());
        em.merge(entity);
    }

    @Override
    public void remove(Long id) {
        Catch entity = em.find(Catch.class, id);
        em.remove(entity);
    }
}
