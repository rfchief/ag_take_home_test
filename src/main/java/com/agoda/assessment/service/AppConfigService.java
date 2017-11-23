package com.agoda.assessment.service;

import com.agoda.assessment.repository.ConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppConfigService {

    private final ConfigRepository configRepository;

    @Autowired
    public AppConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public boolean updateDivideSize(int newDivideSize) {
        configRepository.updateDivideSize(newDivideSize);

        return equalsTo(newDivideSize, configRepository.getDivideSize());
    }

    public boolean updateHotelScore(int newHotelScore) {
        configRepository.updateHotelScore(newHotelScore);

        return equalsTo(newHotelScore, configRepository.getHotelScore());
    }

    public boolean updateCountryScore(int newCountryScore) {
        configRepository.updateCountryScore(newCountryScore);

        return equalsTo(newCountryScore, configRepository.getCountryScore());
    }

    private boolean equalsTo(int newValue, int updatedValue) {
        return StringUtils.equalsIgnoreCase(String.valueOf(newValue), String.valueOf(updatedValue));
    }
}
