package com.visma.fishing.services;

import com.visma.fishing.model.configuration.Configuration;

import java.util.Optional;

public interface ConfigurationService extends Service<Configuration, String> {

    Optional<Configuration> updateConfigurationByKey(String id, Configuration configuration);

    String findValueByKey(String key, String defaultValue);
}
