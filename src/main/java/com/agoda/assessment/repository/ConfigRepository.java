package com.agoda.assessment.repository;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

public class ConfigRepository {

    private Map<String, String> configRepository;

    public ConfigRepository(Map<String, String> configRepository) {
        this.configRepository = configRepository;
    }

    public int getDivideSize(){
        return getInt("divideSize");
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

    public void updateDivideSize(int divideSize) {
        Preconditions.checkArgument(divideSize <= 0, "DivideSize config must be great zero!!");
        configRepository.put("divideSize", String.valueOf(divideSize));
    }

    public void updateHotelScore(int hotelScore) {
        configRepository.put("hotelScore", String.valueOf(hotelScore));
    }

    public void updateCountryScore(int countryScore) {
        configRepository.put("countryScore", String.valueOf(countryScore));
    }
}
