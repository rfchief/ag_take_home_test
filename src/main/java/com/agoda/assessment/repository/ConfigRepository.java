package com.agoda.assessment.repository;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

public class ConfigRepository {

    private Map<String, String> configRepository;

    public ConfigRepository(Map<String, String> configRepository) {
        this.configRepository = configRepository;
    }

    public int getInt(String configName) {
        return NumberUtils.toInt(configRepository.get(configName));
    }
}
