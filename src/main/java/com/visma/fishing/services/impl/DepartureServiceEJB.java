package com.visma.fishing.services.impl;

import com.visma.fishing.model.Departure;
import com.visma.fishing.services.DepartureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.messages.Messages.*;
import static com.visma.fishing.queries.Queries.DEPARTURE_FIND_BY_DATE;
import static com.visma.fishing.queries.Queries.DEPARTURE_FIND_BY_PORT;

@Transactional
@Stateless
public class DepartureServiceEJB implements DepartureService {

    private Logger log = LogManager.getLogger(DepartureServiceEJB.class);


    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Departure> findAll() {
        TypedQuery<Departure> q = em.createNamedQuery("departure.findAll", Departure.class);
        return q.getResultList();
    }

    @Override
    public Optional<Departure> findById(String id) {
        return Optional.ofNullable(em.find(Departure.class, id));
    }

    @Override
    public Departure create(Departure departure) {
        em.persist(departure);
        log.info(DEPARTURE_SAVE_SUCCESS_MSG, departure.getId());
        return departure;
    }

    @Override
    public Optional<Departure> updateDepartureById(String id, Departure departure) {
        return Optional.ofNullable(em.find(Departure.class, id))
                .map(entity -> {
                    entity.setDate(departure.getDate());
                    entity.setPort(departure.getPort());
                    em.merge(entity);
                    return Optional.of(entity);
                }).orElseGet(() -> {
            log.warn(DEPARTURE_FIND_FAILED_MSG, id);
            return Optional.empty();
        });
    }

    @Override
    public void remove(String id) {
        Optional.ofNullable(em.find(Departure.class, id))
                .ifPresent(entity -> {
                    em.remove(entity);
                    log.info(DEPARTURE_REMOVED_SUCCESS_MSG, id);
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Departure> findByPort(String port){
        return em.createNativeQuery(DEPARTURE_FIND_BY_PORT, Departure.class)
                .setParameter(1, port)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Departure> findByPeriod(String start, String end){
        return em.createNativeQuery(DEPARTURE_FIND_BY_DATE, Departure.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }
}
