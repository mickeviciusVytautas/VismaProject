package com.visma.fishing.services.impl;

import com.visma.fishing.model.configuration.Configuration;
import com.visma.fishing.services.ConfigurationService;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.queries.Queries.CONFIGURATION_FIND_START;
import static com.visma.fishing.queries.Queries.CONFIGURATION_FIND_VALUE_BY_KEY;

@Transactional
@Stateless
@Slf4j
public class ConfigurationServiceEJB implements ConfigurationService {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<Configuration> findAll() {
        return em.createNativeQuery(CONFIGURATION_FIND_START, Configuration.class).getResultList();
    }

    @Override
    public Optional<Configuration> findById(String id) {
        return Optional.ofNullable(em.find(Configuration.class, id));
    }

    @Override
    public Configuration create(Configuration configuration) {
        em.persist(configuration);
        return configuration;

    }

    @Override
    public Optional<Configuration> updateConfigurationByKey(String id, Configuration configuration) {
        Configuration entity = em.find(Configuration.class, id);
        if (entity == null) {
            return Optional.empty();
        }
        entity.setKey(configuration.getKey());
        entity.setValue(configuration.getValue());
        entity.setMode(configuration.getMode());
        em.merge(entity);
        return Optional.of(entity);

    }

    @Override
    public void remove(String id) {
        Configuration entity = em.find(Configuration.class, id);
        em.remove(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String findValueByKey(String key, String defaultValue){
        try {
            Configuration configuration = (Configuration) em.createNativeQuery(CONFIGURATION_FIND_VALUE_BY_KEY, Configuration.class)
                    .setParameter(1, key)
                    .getSingleResult();
            return configuration.getValue();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return defaultValue;
    }
}
