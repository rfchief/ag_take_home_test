package com.agoda.assessment.repository.impl;

import com.agoda.assessment.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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
    public boolean isExists(Integer hotelId) {
        return hotelIdStorage.contains(hotelId);
    }

    @Override
    public void remove(Integer hotelId) {
        hotelIdStorage.remove(hotelId);
    }
}
