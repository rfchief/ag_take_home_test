package com.agoda.assessment.repository.impl;

import com.agoda.assessment.repository.HotelRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class HotelRepositoryImpl implements HotelRepository {

    private Set<Integer> hotelIdStorage;

    @Autowired
    public HotelRepositoryImpl(@Qualifier("hotelIdStorage") Set<Integer> hotelIdStorage) {
        this.hotelIdStorage = hotelIdStorage;
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
    public void replace(Set<Integer> newHotelIds) {
        hotelIdStorage = newHotelIds;
    }

    @Override
    public List<Boolean> exists(List<Integer> hotelIds) {
        List<Boolean> exists = Lists.newArrayListWithExpectedSize(hotelIds.size());
        for (Integer hotelId : hotelIds)
            exists.add(hotelIdStorage.contains(hotelId));
        return exists;
    }

}
