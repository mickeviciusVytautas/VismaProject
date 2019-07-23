package com.visma.fishing.services.impl;

import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.services.EndOfFishingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.messages.Messages.*;
import static com.visma.fishing.queries.Queries.END_OF_FISHING_FIND_BY_DATE;
import static com.visma.fishing.queries.Queries.END_OF_FISHING_FIND_START;

@Transactional
@Stateless

public class EndOfFishingServiceEJB implements EndOfFishingService {

    private Logger log = LogManager.getLogger(EndOfFishingServiceEJB.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EndOfFishing> findAll() {
        TypedQuery<EndOfFishing> q = em.createNamedQuery("endOfFishing.findAll", EndOfFishing.class);
        return q.getResultList();
    }

    @Override
    public Optional<EndOfFishing> findById(String id) {
        return Optional.ofNullable(em.find(EndOfFishing.class, id));
    }

    @Override
    public void create(EndOfFishing endOfFishing) {
        em.persist(endOfFishing);
        log.info(END_OF_FISHING_SAVE_SUCCESS_MSG, endOfFishing.getId());
    }

    @Override
    public Optional<EndOfFishing> updateEndOfFishingById(String id, EndOfFishing endOfFishing) {
        return Optional.ofNullable(em.find(EndOfFishing.class, id))
                .map(entity -> {
                    entity.setDate(endOfFishing.getDate());
                    em.merge(entity);
                    log.info(END_OF_FISHING_UPDATE_SUCCESS_MSG, id);
                    return Optional.of(entity);
                }).orElseGet(() -> {
            log.warn(END_OF_FISHING_FIND_FAILED_MSG, id);
            return Optional.empty();
        });


    }

    @Override
    public void remove(String id) {
        Optional.ofNullable(em.find(EndOfFishing.class, id))
                .ifPresent(entity -> {
                    em.remove(entity);
                    log.info(END_OF_FISHING_REMOVED_SUCCESS_MSG, id);
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EndOfFishing> findByPeriod(String start, String end){
        return em.createNativeQuery(END_OF_FISHING_FIND_BY_DATE, EndOfFishing.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }

}
