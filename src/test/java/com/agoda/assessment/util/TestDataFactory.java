package com.agoda.assessment.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class TestDataFactory {

    public static Map<Integer, Set<Integer>> getIdStorage() {
        return getIdStorage(5);
    }

    public static Map<Integer, Set<Integer>> getIdStorage(int partitionSize) {
        return createHotelIdStorageWith(partitionSize, new Integer [] {1000, 2000, 3000, 4000, 5000});
    }

    public static Map<Integer, Set<Integer>> getNewHotelIdStorage(int partitionSize, Integer [] inputs) {
        return createHotelIdStorageWith(partitionSize, inputs);
    }

    public static Set<Integer> getIdsWith(int partitionKey) {
        return getIdsWith(partitionKey, new Integer [] {1000, 2000, 3000, 4000, 5000});
    }

    public static Set<Integer> getIdsWith(int partitionKey, Integer[] inputs) {
        Set<Integer> ids = Sets.newHashSetWithExpectedSize(inputs.length);
        for (Integer input : inputs)
            ids.add(input + partitionKey);
        return ids;
    }

    private static Map<Integer, Set<Integer>> createHotelIdStorageWith(int partitionSize, Integer [] inputs) {
        Map<Integer, Set<Integer>> storage = Maps.newConcurrentMap();
        for (int i = 0; i < partitionSize; i++)
            storage.put(i, Sets.newConcurrentHashSet(getIdsWith(i, inputs)));

        return storage;
    }
}
