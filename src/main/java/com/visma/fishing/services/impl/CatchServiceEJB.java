package com.visma.fishing.services.impl;

import com.visma.fishing.services.CatchService;
import com.visma.fishing.model.Catch;

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
public class CatchServiceEJB implements CatchService {

    private static final String QUERY_FIND_START = "SELECT C.* FROM CATCH C ";
    private static final String QUERY_FIND_BY_SPECIES =
            QUERY_FIND_START
                    + " WHERE C.SPECIES LIKE ?1 ";
    private static final String QUERY_FIND_WITH_BIGGER_WEIGHT =
            QUERY_FIND_START
                    + " WHERE C.WEIGHT >= ?1 ";
    private static final String QUERY_FIND_WITH_LOWER_WEIGHT =
            QUERY_FIND_START
                    + " WHERE C.WEIGHT < ?1 ";

    @PersistenceContext(name = "prod")
    EntityManager em;

    @Override
    public List<Catch> findAll() {
        TypedQuery<Catch> q = em.createNamedQuery("catch.findAll", Catch.class);
        return q.getResultList();

    }

    @Override
    public Response findById(String id) {
        return Optional.ofNullable(em.find(Catch.class, id)).map(
                aCatch -> Response.status(Response.Status.FOUND).entity(aCatch).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Catch by id " + id + " is not found.").build());
    }

    @Override
    public Response create(Catch aCatch) {
        em.persist(aCatch);
        return Response.status(Response.Status.CREATED).entity("Successfully saved catch to database.").build();
    }

    @Override
    public Response update(String id, Catch aCatch) {
        Catch entity = em.find(Catch.class, id);
        entity.setSpecies(aCatch.getSpecies());
        entity.setWeight(aCatch.getWeight());
        em.merge(entity);
        return Response.status(Response.Status.ACCEPTED).entity("Successfully updated catch.").build();
    }

    @Override
    public void remove(String id) {
        Catch entity = em.find(Catch.class, id);
        em.remove(entity);
    }

    @Override
    public List<Catch> findBySpecies(String species){
        return em.createNativeQuery(QUERY_FIND_BY_SPECIES, Catch.class)
                .setParameter(1, species)
                .getResultList();
    }

    @Override
    public List<Catch> findByWeight(Long weight, boolean searchWithLowerWeight){
        if(searchWithLowerWeight){
            return em.createNativeQuery(QUERY_FIND_WITH_LOWER_WEIGHT, Catch.class)
                    .setParameter(1, weight)
                    .getResultList();
        }
        return em.createNativeQuery(QUERY_FIND_WITH_BIGGER_WEIGHT, Catch.class)
                .setParameter(1, weight)
                .getResultList();
    }

}
