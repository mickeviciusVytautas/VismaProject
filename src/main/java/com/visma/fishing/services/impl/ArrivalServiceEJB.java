package com.visma.fishing.services.impl;

import com.visma.fishing.model.Arrival;
import com.visma.fishing.services.ArrivalService;

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
public class ArrivalServiceEJB implements ArrivalService {

    private static final String QUERY_START = "SELECT A.* FROM ARRIVAL A ";
    private static final String QUERY_FIND_BY_PORT =
            QUERY_START
            + " WHERE A.PORT LIKE ?1 ";
    private static final String QUERY_FIND_BY_DATE =
            QUERY_START
            + " WHERE A.DATE BETWEEN ?1 and ?2 ";

            @PersistenceContext
    EntityManager em;

    @Override
    public List<Arrival> findAll() {
        TypedQuery<Arrival> q = em.createNamedQuery("arrival.findAll", Arrival.class);
        return q.getResultList();
    }

    @Override
    public Response findById(String id) {
        return Optional.ofNullable(em.find(Arrival.class, id)).map(
                arrival -> Response.status(Response.Status.FOUND).entity(arrival).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Arrival by id " + id + " is not found.").build());
    }

    @Override
    public Response create(Arrival arrival) {
        em.persist(arrival);
        return Response.ok("Successfully saved arrival to database.").build();

    }

    @Override
    public Response update(String id, Arrival arrival) {
        Arrival entity = em.find(Arrival.class, id);
        entity.setPort(arrival.getPort());
        em.merge(entity);
        return Response.status(Response.Status.ACCEPTED).entity("Successfully updated arrival.").build();

    }

    @Override
    public void remove(String id) {
        Arrival entity = em.find(Arrival.class, id);
        em.remove(entity);
    }

    @Override
    public List<Arrival> findByPort(String port){
        return em.createNativeQuery(QUERY_FIND_BY_PORT, Arrival.class)
                .setParameter(1, port)
                .getResultList();
    }

    @Override
    public List<Arrival> findByPeriod(String start, String end){
        return em.createNativeQuery(QUERY_FIND_BY_DATE, Arrival.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }

}
