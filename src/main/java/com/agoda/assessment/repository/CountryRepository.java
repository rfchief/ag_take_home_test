package com.agoda.assessment.repository;

import java.util.List;

public interface CountryRepository {
    void save(List<Integer> countryIds);

    int countAll();

    boolean exists(Integer countryId);

    void replace(List<Integer> newCountryIds);

    List<Boolean> exists(List<Integer> countryIds);
}
