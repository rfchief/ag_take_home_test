package com.agoda.assessment.parser;

import com.agoda.assessment.model.enums.IdType;
import com.agoda.assessment.model.RequestIdPair;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class IdRepositoryParser {

    private final int partitionSize;

    @Autowired
    public IdRepositoryParser(@Value("${app.repository.partitionSize}") int partitionSize) {
        this.partitionSize = partitionSize;
    }

    public Map<Integer, Set<Integer>> parse(List<RequestIdPair> requestIdPairs, IdType idType) {
        Preconditions.checkArgument(isNotEmpty(requestIdPairs), "Request Id Pairs is Empty!!!");
        return getPartitionedIdsMap(idType, requestIdPairs);
    }

    private Map<Integer, Set<Integer>> getPartitionedIdsMap(IdType idType, List<RequestIdPair> requestIdPairs) {
        Map<Integer, Set<Integer>> partitionedIdsMap = createAndInitIdsMap();
        for (RequestIdPair requestIdPair : requestIdPairs) {
            if(requestIdPair.isNotValid()) {
                log.info("Invalid Request. [Hotel Id : {}, Country Id : {}]", requestIdPair.getHotelId(), requestIdPair.getCountryId());
            } else {
                int inputId = requestIdPair.getIdWith(idType);
                partitionedIdsMap.get(getPartitionKey(inputId)).add(inputId);
            }
        }

        return partitionedIdsMap;
    }

    private int getPartitionKey(int inputId) {
        return inputId % partitionSize;
    }

    private boolean isNotEmpty(List<RequestIdPair> requestIdPairs) {
        return !CollectionUtils.isEmpty(requestIdPairs);
    }

    private Map<Integer, Set<Integer>> createAndInitIdsMap() {
        Map<Integer, Set<Integer>> idsMap = Maps.newHashMap();
        for (int i = 0; i < partitionSize; i++)
            idsMap.put(i, Sets.newConcurrentHashSet());

        return idsMap;
    }
}
