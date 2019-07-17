package com.visma.fishing.services.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.visma.fishing.model.Arrival;
import com.visma.fishing.services.ArrivalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.messages.Messages.*;
import static com.visma.fishing.queries.Queries.ARRIVAL_FIND_BY_DATE;
import static com.visma.fishing.queries.Queries.ARRIVAL_FIND_BY_PORT;

@Transactional
@Stateless
public class ArrivalServiceEJB implements ArrivalService {

    private Logger log = LogManager.getLogger(ConfigurationServiceEJB.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Arrival> findAll() {
        TypedQuery<Arrival> q = em.createNamedQuery("arrival.findAll", Arrival.class);
        return q.getResultList();
    }

    @Override
    public Optional<Arrival> findById(String id) {
        return Optional.ofNullable(em.find(Arrival.class, id));
    }

    @Override
    public Arrival create(Arrival arrival) {
        em.persist(arrival);
        log.info(ARRIVAL_SAVE_SUCCESS_MSG, arrival.getId());
        return arrival;
    }

    @Override
    public Optional<Arrival> updateArrivalById(String id, Arrival arrival) {
        return Optional.ofNullable(em.find(Arrival.class, id))
                .map(entity -> {
                    entity.setPort(arrival.getPort());
                    em.merge(entity);
                    log.info(ARRIVAL_UPDATE_SUCCESS_MSG, id);
                    return Optional.of(entity);
                }).orElseGet(() -> {
                    log.warn(ARRIVAL_FIND_FAILED_MSG, id);
                    return Optional.empty();
                });
    }

    @Override
    public void remove(String id) {
        Optional.ofNullable(em.find(Arrival.class, id))
                .ifPresent(entity -> {
                    em.remove(entity);
                    log.info(ARRIVAL_REMOVED_SUCCESS_MSG, id);
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Arrival> findByPort(String port){
        return em.createNativeQuery(ARRIVAL_FIND_BY_PORT, Arrival.class)
                .setParameter(1, port)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Arrival> findByPeriod(Date start, Date end) {
        return em.createNativeQuery(ARRIVAL_FIND_BY_DATE, Arrival.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }

}
