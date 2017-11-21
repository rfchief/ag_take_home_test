package com.agoda.assessment.service;

import com.agoda.assessment.component.IdRepositoryParser;
import com.agoda.assessment.enums.IdType;
import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.repository.IdRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public void write(List<RequestIdPair> requestIdPairs) {
        log.debug("Start process for replace ID pairs. [Total Size : {}]", requestIdPairs.size());
        StopWatch sw = new StopWatch();
        sw.reset();
        sw.start();

        doReplaceIds(requestIdPairs);

        sw.stop();
        log.debug("End of process for replacing ID pairs. [Execution time : {} sec]", sw.toString());
    }

    private void doReplaceIds(List<RequestIdPair> requestIdPairs) {
        Map<Integer, Set<Integer>> partitionedHotelIdsMap = getPartitionedIdMap(requestIdPairs, IdType.HOTEL);
        Map<Integer, Set<Integer>> partitionedCountryIdsMap = getPartitionedIdMap(requestIdPairs, IdType.COUNTRY);

        writeHotelIds(partitionedHotelIdsMap);
        writeCountryIds(partitionedCountryIdsMap);
    }

    private void writeCountryIds(Map<Integer, Set<Integer>> partitionedCountryIdsMap) {
        for (Integer partitionKey : partitionedCountryIdsMap.keySet())
            countryIdRepository.replace(partitionKey, partitionedCountryIdsMap.get(partitionKey));
    }

    private void writeHotelIds(Map<Integer, Set<Integer>> partitionedHotelIdsMap) {
        for (Integer partitionKey : partitionedHotelIdsMap.keySet())
            hotelIdRepository.replace(partitionKey, partitionedHotelIdsMap.get(partitionKey));
    }

    private Map<Integer, Set<Integer>> getPartitionedIdMap(List<RequestIdPair> requestIdPairs, IdType type) {
        return idRepositoryParser.parse(requestIdPairs, type);
    }
}
