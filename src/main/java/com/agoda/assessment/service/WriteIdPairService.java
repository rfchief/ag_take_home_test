package com.agoda.assessment.service;

import com.agoda.assessment.component.IdRepositoryParser;
import com.agoda.assessment.repository.IdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WriteIdPairService {

    private final IdRepositoryParser idRepositoryParser;
    private final IdRepository hotelIdRepository;
    private final IdRepository countryIdRepository;

    @Autowired
    public WriteIdPairService(IdRepositoryParser idRepositoryParser,
                              @Qualifier("hotelIdRepository") IdRepository hotelIdRepository,
                              @Qualifier("hotelIdRepository") IdRepository countryIdRepository) {
        this.idRepositoryParser = idRepositoryParser;
        this.hotelIdRepository = hotelIdRepository;
        this.countryIdRepository = countryIdRepository;
    }
}
