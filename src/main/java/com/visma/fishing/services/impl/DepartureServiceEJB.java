package com.visma.fishing.services.impl;

import com.visma.fishing.model.Departure;
import com.visma.fishing.services.DepartureService;

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

    private static final String QUERY_START = "SELECT D.* FROM DEPARTURE D ";
    private static final String QUERY_FIND_BY_PORT =
            QUERY_START
                    + " WHERE D.PORT LIKE ?1 ";
    private static final String QUERY_FIND_BY_DATE =
            QUERY_START
                    + " WHERE D.DATE BETWEEN ?1 and ?2 ";

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Departure> findAll() {
        TypedQuery<Departure> q = em.createNamedQuery("departure.findAll", Departure.class);
        return q.getResultList();
    }

    @Override
    public Response findById(String id) {
        return Optional.ofNullable(em.find(Departure.class, id)).map(
                departure -> Response.status(Response.Status.FOUND).entity(departure).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Departure by id " + id + " is not found.").build());
    }

    @Override
    public Response create(Departure departure) {
        em.persist(departure);
        return Response.status(Response.Status.CREATED).entity("Successfully saved departure to database.").build();
    }

    @Override
    public Response update(String id, Departure departure) {
        Departure entity = em.find(Departure.class, id);
        entity.setDate(departure.getDate());
        entity.setPort(departure.getPort());
        em.merge(entity);
        return Response.status(Response.Status.ACCEPTED).entity("Successfully updated departure.").build();
    }

    @Override
    public void remove(String id) {
        Departure entity = em.find(Departure.class, id);
        em.remove(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Departure> findByPort(String port){
        return em.createNativeQuery(QUERY_FIND_BY_PORT, Departure.class)
                .setParameter(1, port)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Departure> findByPeriod(String start, String end){
        return em.createNativeQuery(QUERY_FIND_BY_DATE, Departure.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }
}
