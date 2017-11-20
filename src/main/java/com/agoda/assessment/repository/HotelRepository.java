package com.agoda.assessment.repository;

import java.util.List;

public interface HotelRepository {
    void save(List<Integer> hotelIds);

    int countAll();

    boolean exists(Integer hotelId);

    void replace(List<Integer> newHotelIds);

    List<Boolean> exists(List<Integer> hotelIds);
}
