package com.agoda.assessment.repository.impl;

import com.agoda.assessment.repository.CountryRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class CountryRepositoryImpl implements CountryRepository {

    private Set<Integer> countryIdStorage;

    @Autowired
    public CountryRepositoryImpl(@Qualifier("countryIdStorage") Set<Integer> countryIdStorage) {
        this.countryIdStorage = countryIdStorage;
    }

    @Override
    public int countAll() {
        return countryIdStorage.size();
    }

    @Override
    public boolean exists(Integer countryId) {
        return countryIdStorage.contains(countryId);
    }

    @Override
    public void replace(Set<Integer> newCountryIds) {
        countryIdStorage = newCountryIds;
    }

    @Override
    public List<Boolean> exists(List<Integer> countryIds) {
        List<Boolean> exists = Lists.newArrayListWithExpectedSize(countryIds.size());
        for (Integer countryId : countryIds)
            exists.add(countryIdStorage.contains(countryId));
        return exists;
    }
}
