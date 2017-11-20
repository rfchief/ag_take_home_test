package com.agoda.assessment.repository.impl;

import com.agoda.assessment.repository.IdRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class IdRepositoryImpl implements IdRepository {
    private final int partitionSize;
    private Map<Integer, Set<Integer>> idStorage;
    private int idTotalCount;

    public IdRepositoryImpl(int partitionSize) {
        this.partitionSize = partitionSize;
        this.idStorage = initStorage(partitionSize);
        this.idTotalCount = 0;
    }

    @Override
    public int countAll() {
        return idTotalCount;
    }

    @Override
    public boolean exists(Integer id) {
        Set<Integer> ids = getIds(id);
        return ids.contains(id);
    }

    @Override
    public void replace(int partitionKey, Set<Integer> newIds) {
        int oldIdSize = idStorage.get(partitionKey).size();
        idStorage.put(partitionKey, newIds);
        updateRecordCount(oldIdSize, newIds.size());
    }

    @Override
    public List<Boolean> exists(List<Integer> ids) {
        List<Boolean> exists = Lists.newArrayListWithExpectedSize(ids.size());
        for (Integer hotelId : ids)
            exists.add(exists(hotelId));

        return exists;
    }

    private Set<Integer> getIds(Integer hotelId) {
        return idStorage.get(hotelId % partitionSize);
    }

    private void updateRecordCount(int oldIdsSize, int newIdsSize) {
        idTotalCount = idTotalCount - oldIdsSize + newIdsSize;
    }

    private Map<Integer, Set<Integer>> initStorage(int partitionSize) {
        Map<Integer, Set<Integer>> storage = Maps.newConcurrentMap();
        for (int i = 0; i < partitionSize; i++)
            storage.put(i, Sets.newConcurrentHashSet());

        return storage;
    }
}
