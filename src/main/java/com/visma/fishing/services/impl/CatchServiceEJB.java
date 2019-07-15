package com.visma.fishing.services.impl;

import com.visma.fishing.services.CatchService;
import com.visma.fishing.model.Catch;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.queries.Queries.*;

@Transactional
@Stateless
public class CatchServiceEJB implements CatchService {


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
        return aCatch;
    }

    @Override
    public Optional<Catch> updateCatchById(String id, Catch aCatch) {
        Catch entity = em.find(Catch.class, id);
        if (entity == null) {
            return Optional.empty();
        }
        entity.setSpecies(aCatch.getSpecies());
        entity.setWeight(aCatch.getWeight());
        em.merge(entity);
        return Optional.of(aCatch);
    }

    @Override
    public void remove(String id) {
        Catch entity = em.find(Catch.class, id);
        em.remove(entity);
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
