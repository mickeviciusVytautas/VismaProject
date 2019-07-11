package com.visma.fishing.services.impl;

import com.visma.fishing.model.configuration.Configuration;
import com.visma.fishing.services.ConfigurationService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Transactional
@Stateless
public class ConfigurationServiceEJB implements ConfigurationService {

    private static final String QUERY_SELECT_START = "SELECT C.* from CONFIGURATION C";
    private static final String QUERY_SELECT_VALUE_BY_KEY = QUERY_SELECT_START
            + " WHERE C.KEY = ?1";

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<Configuration> findAll() {
        return em.createNativeQuery(QUERY_SELECT_START, Configuration.class).getResultList();
    }

    @Override
    public Response findById(String id) {
        return Optional.ofNullable(em.find(Configuration.class, id)).map(
                configuration -> Response.status(Response.Status.FOUND).entity(configuration).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Configuration by id " + id + " is not found.").build());
    }

    @Override
    public Response create(Configuration configuration) {
        em.persist(configuration);
        return Response.status(Response.Status.CREATED).entity("Successfully saved " + configuration.getKey() + " configuration to database.").build();

    }

    @Override
    public Response update(String id, Configuration configuration) {
        Configuration entity = em.find(Configuration.class, id);
        entity.setKey(configuration.getKey());
        entity.setValue(configuration.getValue());
        entity.setMode(configuration.getMode());
        em.merge(entity);
        return Response.status(Response.Status.ACCEPTED).entity("Successfully updated configuration " + configuration.getKey() + ".").build();

    }

    @Override
    public void remove(String id) {
        Configuration entity = em.find(Configuration.class, id);
        em.remove(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String findValueByKey(String key, String defaultValue){
        Optional<Configuration> configuration = em.createNativeQuery(QUERY_SELECT_VALUE_BY_KEY, Configuration.class)
                .setParameter(1, key)
                .getResultList().stream().findFirst();
        return configuration.map(Configuration::getValue).orElse(defaultValue);

    }
}
