package com.agoda.assessment.component;

import com.agoda.assessment.enums.IdType;
import com.agoda.assessment.model.RequestIdPair;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class IdRepositoryParser {

    private final int partitionSize;

    @Autowired
    public IdRepositoryParser(@Value("app.repository.partitionSize") int partitionSize) {
        this.partitionSize = partitionSize;
    }

    public Map<Integer, Set<Integer>> parse(List<RequestIdPair> requestIdPairs, IdType idType) {
        Preconditions.checkArgument(isNotEmpty(requestIdPairs), "Request Id Pairs is Empty!!!");
        return getPartitionedIdsMap(idType, requestIdPairs);
    }

    private Map<Integer, Set<Integer>> getPartitionedIdsMap(IdType idType, List<RequestIdPair> requestIdPairs) {
        Map<Integer, Set<Integer>> idsMap = createAndInitIdsMap();
        for (RequestIdPair requestIdPair : requestIdPairs) {
            if(requestIdPair.isValid()) {
                int inputId = requestIdPair.getIdWith(idType);
                idsMap.get(getPartitionKey(inputId)).add(inputId);
            }
        }

        return idsMap;
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
            idsMap.put(i, Sets.newHashSet());

        return idsMap;
    }
}
