package com.agoda.assessment.repository;

import java.util.List;
import java.util.Set;

public interface HotelRepository {
    int countAll();

    boolean exists(Integer hotelId);

    void replace(Set<Integer> newHotelIds);

    List<Boolean> exists(List<Integer> hotelIds);
}
