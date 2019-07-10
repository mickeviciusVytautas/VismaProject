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

    private static final String QUERY_START = "SELECT E.* FROM ENDOFFISHING E ";
    private static final String QUERY_FIND_BY_DATE =
            QUERY_START
                    + " WHERE E.DATE BETWEEN ?1 and ?2 ";

    @PersistenceContext
    EntityManager em;

    @Override
    public List<EndOfFishing> findAll() {
        TypedQuery<EndOfFishing> q = em.createNamedQuery("endOfFishing.findAll", EndOfFishing.class);
        return q.getResultList();
    }

    @Override
    public Response findById(String id) {
        return Optional.ofNullable(em.find(EndOfFishing.class, id))
                .map(endOfFishing -> Response.status(Response.Status.FOUND).entity(endOfFishing).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("EndOfFishing by id " + id + " was not found.").build());
    }

    @Override
    public Response create(EndOfFishing endOfFishing) {
        em.persist(endOfFishing);
        return Response.status(Response.Status.CREATED).entity("Successfully saved endOfFishing to database.").build();
    }

    @Override
    public Response update(String id, EndOfFishing endOfFishing) {
        EndOfFishing entity = em.find(EndOfFishing.class, id);
        entity.setDate(endOfFishing.getDate());
        return Response.status(Response.Status.ACCEPTED).entity("Successfully updated endOfFishing.").build();
    }

    @Override
    public void remove(String id) {
        EndOfFishing entity = em.find(EndOfFishing.class, id);
        em.remove(entity);
    }

    @Override
    public List<EndOfFishing> findByPeriod(String start, String end){
        return em.createNativeQuery(QUERY_FIND_BY_DATE, EndOfFishing.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }
}
