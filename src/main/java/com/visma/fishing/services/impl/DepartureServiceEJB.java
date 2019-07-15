package com.visma.fishing.services.impl;

import com.visma.fishing.model.Departure;
import com.visma.fishing.services.DepartureService;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.auxiliary.Messages.DEPARTURE_REMOVED_SUCCESS_MSG;
import static com.visma.fishing.queries.Queries.DEPARTURE_FIND_BY_DATE;
import static com.visma.fishing.queries.Queries.DEPARTURE_FIND_BY_PORT;

@Transactional
@Stateless
@Slf4j
public class DepartureServiceEJB implements DepartureService {

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
        return departure;
    }

    @Override
    public Optional<Departure> updateDepartureById(String id, Departure departure) {
        Departure entity = em.find(Departure.class, id);
        if (entity == null) {
            return Optional.empty();
        }
        entity.setDate(departure.getDate());
        entity.setPort(departure.getPort());
        em.merge(entity);
        return Optional.of(entity);
    }

    @Override
    public void remove(String id) {
        Optional<Departure> optional = Optional.ofNullable(em.find(Departure.class, id));
        optional.ifPresent(entity -> {
            em.remove(entity);
            log.info(DEPARTURE_REMOVED_SUCCESS_MSG + entity.getId());
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
