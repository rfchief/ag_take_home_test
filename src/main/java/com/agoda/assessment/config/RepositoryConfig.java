package com.agoda.assessment.config;

import com.agoda.assessment.repository.ConfigRepository;
import com.agoda.assessment.repository.IdRepository;
import com.agoda.assessment.repository.impl.IdRepositoryImpl;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
public class RepositoryConfig {

    @Bean(name = "hotelIdRepository")
    public IdRepository getHotelIdRepository(@Value("${app.repository.partitionSize}") int partitionSize) {
        if(isNotValid(partitionSize))
            throw new BeanInitializationException(String.format("Fail to initialize HotelIdRepository because partition size is not valid. [Partition Size : %d]", partitionSize));

        return new IdRepositoryImpl(partitionSize);
    }

    @Bean(name = "countryIdRepository")
    public IdRepository getCountryIdRepository(@Value("${app.repository.partitionSize}") int partitionSize) {
        if(isNotValid(partitionSize))
            throw new BeanInitializationException(String.format("Fail to initialize CountryIdRepository because partition size is not valid. [Partition Size : %d]", partitionSize));

        return new IdRepositoryImpl(partitionSize);
    }

    @Bean(name = "configRepository")
    public ConfigRepository getConfigRepository() {
        return new ConfigRepository(initConfigMap());
    }

    private Map<String, String> initConfigMap() {
        Map<String, String> configMap = Maps.newHashMap();
        configMap.put("divideSize", "10");
        configMap.put("hotelScore", "5");
        configMap.put("countryScore", "3");

        return configMap;
    }

    private boolean isNotValid(int partitionSize) {
        return partitionSize < 0;
    }
}
