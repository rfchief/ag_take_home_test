package com.agoda.assessment.repository.impl;

import com.agoda.assessment.repository.HotelRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class HotelRepositoryImpl implements HotelRepository {

    private final CopyOnWriteArraySet<Integer> hotelIdStorage;

    @Autowired
    public HotelRepositoryImpl(@Qualifier("hotelIdStorage") CopyOnWriteArraySet<Integer> hotelIdStorage) {
        this.hotelIdStorage = hotelIdStorage;
    }

    @Override
    public void save(List<Integer> hotelIds) {
        if(CollectionUtils.isEmpty(hotelIds))
            return;

        hotelIdStorage.addAll(hotelIds);
    }

    @Override
    public int countAll() {
        return hotelIdStorage.size();
    }

    @Override
    public boolean exists(Integer hotelId) {
        return hotelIdStorage.contains(hotelId);
    }

    @Override
    public void replace(List<Integer> newHotelIds) {
        hotelIdStorage.clear();
        hotelIdStorage.addAll(newHotelIds);
    }

    @Override
    public List<Boolean> exists(List<Integer> hotelIds) {
        List<Boolean> exists = Lists.newArrayListWithExpectedSize(hotelIds.size());
        for (Integer hotelId : hotelIds)
            exists.add(hotelIdStorage.contains(hotelId));
        return exists;
    }

}
