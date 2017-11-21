package com.agoda.assessment.util;

import com.agoda.assessment.model.RequestIdPair;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

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

    public static List<RequestIdPair> getRequestIdPairsWithExpectedSize(int expectedSize) {
        List<RequestIdPair> result = Lists.newArrayListWithExpectedSize(expectedSize);
        for (int i = 0; i < expectedSize; i++)
            result.add(getNewRequestIdPair());

        return result;
    }

    public static List<RequestIdPair> getInvalidRequestIdPairsWithExpectedSize(int expectedSize) {
        List<RequestIdPair> result = Lists.newArrayListWithExpectedSize(expectedSize);
        for (int i = 0; i < expectedSize; i++)
            result.add(getInvalidRequestIdPair());

        return result;
    }

    private static RequestIdPair getInvalidRequestIdPair() {
        int hotelId = getRandomNumber();
        int countryId = getRandomNumber();
        if(hotelId == countryId)
            countryId++;


        return new RequestIdPair(getNegativeNumber(hotelId), getNegativeNumber(countryId));
    }

    private static int getNegativeNumber(int inputNumber) {
        return inputNumber < 0 ? inputNumber : inputNumber * -1;
    }

    private static Map<Integer, Set<Integer>> createHotelIdStorageWith(int partitionSize, Integer [] inputs) {
        Map<Integer, Set<Integer>> storage = Maps.newConcurrentMap();
        for (int i = 0; i < partitionSize; i++)
            storage.put(i, Sets.newConcurrentHashSet(getIdsWith(i, inputs)));

        return storage;
    }

    private static RequestIdPair getNewRequestIdPair() {
        int hotelId = Math.abs(getRandomNumber());
        int countryId = Math.abs(getRandomNumber());

        if(hotelId == countryId)
            countryId--;

        return new RequestIdPair(hotelId, countryId);
    }

    private static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt();
    }

}
