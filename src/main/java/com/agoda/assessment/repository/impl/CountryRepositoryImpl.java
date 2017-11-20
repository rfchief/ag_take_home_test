package com.agoda.assessment.repository.impl;

import com.agoda.assessment.repository.CountryRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class CountryRepositoryImpl implements CountryRepository {

    private final CopyOnWriteArraySet<Integer> countryIdStorage;

    @Autowired
    public CountryRepositoryImpl(@Qualifier("countryIdStorage") CopyOnWriteArraySet<Integer> countryIdStorage) {
        this.countryIdStorage = countryIdStorage;
    }

    @Override
    public void save(List<Integer> countryIds) {
        countryIdStorage.addAll(countryIds);
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
    public void replace(List<Integer> newCountryIds) {
        countryIdStorage.clear();
        countryIdStorage.addAll(newCountryIds);
    }

    @Override
    public List<Boolean> exists(List<Integer> countryIds) {
        List<Boolean> exists = Lists.newArrayListWithExpectedSize(countryIds.size());
        for (Integer countryId : countryIds)
            exists.add(countryIdStorage.contains(countryId));
        return exists;
    }
}
