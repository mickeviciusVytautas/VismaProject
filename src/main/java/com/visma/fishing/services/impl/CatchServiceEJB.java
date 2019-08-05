package com.visma.fishing.services.impl;

import com.visma.fishing.exception.ConcurrentChangesException;
import com.visma.fishing.exception.EntityNotFoundException;
import com.visma.fishing.model.Catch;
import com.visma.fishing.services.CatchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.messages.Messages.CATCH_CONCURRENT_CHANGES_MSG;
import static com.visma.fishing.messages.Messages.CATCH_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.CATCH_REMOVED_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.CATCH_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.CATCH_UPDATE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.format;
import static com.visma.fishing.queries.Queries.CATCH_FIND_BY_SPECIES;
import static com.visma.fishing.queries.Queries.CATCH_FIND_WITH_BIGGER_WEIGHT;
import static com.visma.fishing.queries.Queries.CATCH_FIND_WITH_LOWER_WEIGHT;

@Transactional
@Stateless
public class CatchServiceEJB implements CatchService {

    private Logger log = LogManager.getLogger(ConfigurationServiceEJB.class);

    @PersistenceContext(name = "prod")
    private EntityManager em;

    @Override
    public List<Catch> findAll() {
        TypedQuery<Catch> q = em.createNamedQuery("catch.findAll", Catch.class);
        return q.getResultList();
    }

    @Override
    public Optional<Catch> findById(Long id) {
        return Optional.ofNullable(em.find(Catch.class, id));
    }

    @Override
    public void create(Catch aCatch) {
        em.persist(aCatch);
        log.info(CATCH_SAVE_SUCCESS_MSG, aCatch.getId());
    }

    @Override
    public void updateCatch(Catch aCatch) {
        catchExists(aCatch.getId());
        try {
            em.merge(aCatch);
            em.flush();
        } catch (OptimisticLockException e) {
            log.error(CATCH_CONCURRENT_CHANGES_MSG, aCatch.getId());
            throw new ConcurrentChangesException(format(CATCH_CONCURRENT_CHANGES_MSG, aCatch.getId()));
        }
        log.info(CATCH_UPDATE_SUCCESS_MSG, aCatch.getId());
    }

    private void catchExists(Long id) {
        if (em.find(Catch.class, id) == null) {
            log.info(CATCH_FIND_FAILED_MSG, id);
            throw new EntityNotFoundException(format(CATCH_FIND_FAILED_MSG, id));
        }
    }
    @Override
    public void remove(Long id) {
        Optional.ofNullable(em.find(Catch.class, id))
                .ifPresent(entity -> {
                    em.remove(entity);
                    log.info(CATCH_REMOVED_SUCCESS_MSG, id);
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Catch> findBySpecies(String species) {
        return em.createNativeQuery(CATCH_FIND_BY_SPECIES, Catch.class)
                .setParameter(1, species)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Catch> findByWeight(Long weight, boolean searchWithLowerWeight) {
        if (searchWithLowerWeight) {
            return em.createNativeQuery(CATCH_FIND_WITH_LOWER_WEIGHT, Catch.class)
                    .setParameter(1, weight)
                    .getResultList();
        }
        return em.createNativeQuery(CATCH_FIND_WITH_BIGGER_WEIGHT, Catch.class)
                .setParameter(1, weight)
                .getResultList();
    }

}
