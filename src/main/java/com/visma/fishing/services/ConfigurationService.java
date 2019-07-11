package com.visma.fishing.services;

import com.visma.fishing.model.configuration.Configuration;

public interface ConfigurationService extends Service<Configuration, String> {
    String findValueByKey(String key, String defaultValue);
}
