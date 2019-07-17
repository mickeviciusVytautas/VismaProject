package com.visma.fishing.services.impl;

import com.visma.fishing.services.CatchService;
import com.visma.fishing.model.Catch;
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
import static com.visma.fishing.queries.Queries.*;

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
    public Optional<Catch> findById(String id) {
        return Optional.ofNullable(em.find(Catch.class, id));
    }

    @Override
    public Catch create(Catch aCatch) {
        em.persist(aCatch);
        log.info(CATCH_SAVE_SUCCESS_MSG, aCatch.getId());
        return aCatch;
    }

    @Override
    public Optional<Catch> updateCatchById(String id, Catch aCatch) {
        return Optional.ofNullable(em.find(Catch.class, id))
                .map(entity -> {
                    entity.setSpecies(aCatch.getSpecies());
                    entity.setWeight(aCatch.getWeight());
                    em.merge(entity);
                    log.info(CATCH_UPDATE_SUCCESS_MSG, id);
                    return Optional.of(entity);
                }).orElseGet(() -> {
                    log.warn(CATCH_FIND_FAILED_MSG, id);
                    return Optional.empty();
                });
    }

    @Override
    public void remove(String id) {
        Optional.ofNullable(em.find(Catch.class, id))
                .ifPresent(entity -> {
                    em.remove(entity);
                    log.info(CATCH_REMOVED_SUCCESS_MSG, id);
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Catch> findBySpecies(String species){
        return em.createNativeQuery(CATCH_FIND_BY_SPECIES, Catch.class)
                .setParameter(1, species)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Catch> findByWeight(Long weight, boolean searchWithLowerWeight){
        if(searchWithLowerWeight){
            return em.createNativeQuery(CATCH_FIND_WITH_LOWER_WEIGHT, Catch.class)
                    .setParameter(1, weight)
                    .getResultList();
        }
        return em.createNativeQuery(CATCH_FIND_WITH_BIGGER_WEIGHT, Catch.class)
                .setParameter(1, weight)
                .getResultList();
    }

}
