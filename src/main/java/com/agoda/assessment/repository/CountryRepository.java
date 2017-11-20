package com.agoda.assessment.repository;

import java.util.List;
import java.util.Set;

public interface CountryRepository {
    int countAll();

    boolean exists(Integer countryId);

    void replace(Set<Integer> newCountryIds);

    List<Boolean> exists(List<Integer> countryIds);
}
