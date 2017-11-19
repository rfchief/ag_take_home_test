package com.agoda.assessment.repository;

import java.util.List;

public interface HotelRepository {
    void save(List<Integer> hotelIds);

    int countAll();

    boolean isExists(Integer hotelId);

    void remove(Integer hotelId);
}
