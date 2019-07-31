package com.visma.fishing.services.impl;

import com.visma.fishing.model.configuration.Configuration;
import com.visma.fishing.services.ConfigurationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.messages.Messages.CONFIGURATION_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.CONFIGURATION_REMOVED_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.CONFIGURATION_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.CONFIGURATION_UPDATE_SUCCESS_MSG;
import static com.visma.fishing.queries.Queries.CONFIGURATION_FIND_START;
import static com.visma.fishing.queries.Queries.CONFIGURATION_FIND_VALUE_BY_KEY;

@Transactional
@Stateless
public class ConfigurationServiceEJB implements ConfigurationService {

    private Logger log = LogManager.getLogger(ConfigurationServiceEJB.class);

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<Configuration> findAll() {
        return em.createNativeQuery(CONFIGURATION_FIND_START, Configuration.class).getResultList();
    }

    @Override
    public Optional<Configuration> findById(String key) {
        return Optional.ofNullable(em.find(Configuration.class, key));
    }

    @Override
    public void create(Configuration configuration) {
        em.persist(configuration);
        log.info(CONFIGURATION_SAVE_SUCCESS_MSG, configuration.getKey());

    }

    @Override
    public Optional<Configuration> updateConfigurationByKey(String key, Configuration configuration) {
        return Optional.ofNullable(em.find(Configuration.class, key))
                .map(entity -> {
                    entity.setKey(configuration.getKey());
                    entity.setValue(configuration.getValue());
                    entity.setMode(configuration.getMode());
                    em.merge(entity);
                    log.info(CONFIGURATION_UPDATE_SUCCESS_MSG, key);
                    return Optional.of(entity);
                }).orElseGet(() -> {
                    log.warn(CONFIGURATION_FIND_FAILED_MSG, key);
                    return Optional.empty();
                });
    }

    @Override
    public void remove(String key) {
        Optional.ofNullable(em.find(Configuration.class, key))
                .ifPresent(entity -> {
                    em.remove(entity);
                    log.info(CONFIGURATION_REMOVED_SUCCESS_MSG, key);
                });
    }

    @Override
    public String findValueByKey(String key, String defaultValue) {
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
