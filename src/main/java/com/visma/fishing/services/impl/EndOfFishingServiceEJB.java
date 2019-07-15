package com.visma.fishing.services.impl;

import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.services.EndOfFishingService;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.auxiliary.Messages.END_OF_FISHING_REMOVED_SUCCESS_MSG;
import static com.visma.fishing.auxiliary.Messages.END_OF_FISHING_UPDATE_SUCCESS_MSG;
import static com.visma.fishing.queries.Queries.END_OF_FISHING_FIND_BY_DATE;

@Transactional
@Stateless
@Slf4j
public class EndOfFishingServiceEJB implements EndOfFishingService {

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
    public EndOfFishing create(EndOfFishing endOfFishing) {
        em.persist(endOfFishing);
        return endOfFishing;
    }

    @Override
    public Optional<EndOfFishing> updateEndOfFishingById(String id, EndOfFishing endOfFishing) {
        EndOfFishing entity = em.find(EndOfFishing.class, id);
        if (entity == null) {
            return Optional.empty();
        }
        entity.setDate(endOfFishing.getDate());
        em.merge(entity);
        log.info(END_OF_FISHING_UPDATE_SUCCESS_MSG + entity.getId());
        return Optional.of(entity);

    }

    @Override
    public void remove(String id) {
        Optional<EndOfFishing> optional = Optional.ofNullable(em.find(EndOfFishing.class, id));
        optional.ifPresent(entity -> {
            em.remove(entity);
            log.info(END_OF_FISHING_REMOVED_SUCCESS_MSG + entity.getId());
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
