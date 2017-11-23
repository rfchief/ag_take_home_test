package com.agoda.assessment.repository;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

public class ConfigRepository {

    private Map<String, String> configRepository;

    public ConfigRepository(Map<String, String> configRepository) {
        this.configRepository = configRepository;
    }

    public int getDevideSize(){
        return getInt("devideSize");
    }

    public int getHotelScore(){
        return getInt("hotelScore");
    }

    public int getCountryScore() {
        return getInt("countryScore");
    }

    private int getInt(String configName) {
        return NumberUtils.toInt(configRepository.get(configName));
    }
}
