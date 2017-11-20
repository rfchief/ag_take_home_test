package com.agoda.assessment;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class TestDataFactory {

    public static CopyOnWriteArraySet<Integer> getHotelIdStorage() {
        CopyOnWriteArraySet<Integer> storage = Sets.newCopyOnWriteArraySet();
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
