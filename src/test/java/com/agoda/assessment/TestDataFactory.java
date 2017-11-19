package com.agoda.assessment;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class TestDataFactory {

    public static CopyOnWriteArraySet<Integer> getHotelIdStorage() {
        CopyOnWriteArraySet<Integer> storage = Sets.newCopyOnWriteArraySet();
        ArrayList<Integer> initHotelIds = Lists.newArrayList(1, 2, 3, 4);
        storage.addAll(initHotelIds);

        return storage;
    }

}
