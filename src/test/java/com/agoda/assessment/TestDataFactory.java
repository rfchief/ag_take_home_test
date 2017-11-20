package com.agoda.assessment;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class TestDataFactory {

    public static Set<Integer> getHotelIdStorage() {
        Set<Integer> storage = Sets.newConcurrentHashSet();
        storage.addAll(getHotelIds());

        return storage;
    }

    public static List<Integer> getHotelIds() {
        return getHotelIds(new Integer [] {1,2,3,4});
    }

    public static List<Integer> getHotelIds(Integer [] inputs) {
        return Lists.newArrayList(inputs);
    }

}
