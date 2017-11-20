package com.agoda.assessment.config;

import com.agoda.assessment.repository.IdRepository;
import com.agoda.assessment.repository.impl.IdRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    private boolean isNotValid(@Value("app.repository.partition.size") int partitionSize) {
        return partitionSize < 0;
    }
}
