package com.agoda.assessment.service;

import com.agoda.assessment.enums.IdType;
import com.agoda.assessment.model.HotelIdScore;
import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.repository.ConfigRepository;
import com.agoda.assessment.repository.IdRepository;
import com.agoda.assessment.strategy.CalculateStrategy;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ReadIdPairService {

    private final IdRepository hotelIdRepository;
    private final IdRepository countryIdRepository;
    private final ConfigRepository configRepository;
    private final CalculateStrategy calculateStrategy;

    @Autowired
    public ReadIdPairService(@Qualifier("hotelIdRepository") IdRepository hotelIdRepository,
                             @Qualifier("countryIdRepository") IdRepository countryIdRepository,
                             @Qualifier("configRepository") ConfigRepository configRepository,
                             @Qualifier("basicCalculateStrategy")CalculateStrategy calculateStrategy) {
        this.hotelIdRepository = hotelIdRepository;
        this.countryIdRepository = countryIdRepository;
        this.configRepository = configRepository;
        this.calculateStrategy = calculateStrategy;
    }

    public List<HotelIdScore> read(List<RequestIdPair> requestIdPairs) {
        if(isEmpty(requestIdPairs))
            return Lists.newArrayList();

        return getHotelIdScores(requestIdPairs, getHotelScore(), getCountryScore());
    }

    public List<HotelIdScore> readByAsync(List<RequestIdPair> requestIdPairs) {
        if(isEmpty(requestIdPairs))
            return Lists.newArrayList();

        int hotelScore = getHotelScore();
        int countryScore = getCountryScore();

        List<List<RequestIdPair>> dividedRequests = divideRequestIdPairs(requestIdPairs, getDivideSize());
        List<CompletableFuture<List<HotelIdScore>>> completableFutures = Lists.newArrayListWithExpectedSize(dividedRequests.size());

        for (List<RequestIdPair> dividedRequest : dividedRequests)
            completableFutures.add(getHotelIdScoresByAsync(dividedRequest, hotelScore, countryScore));

        List<HotelIdScore> hotelIdScores = Lists.newArrayList();

        return hotelIdScores;
    }

    @Async
    private CompletableFuture<List<HotelIdScore>> getHotelIdScoresByAsync(List<RequestIdPair> dividedRequest, int hotelScore, int countryScore) {
        return CompletableFuture.completedFuture(getHotelIdScores(dividedRequest, hotelScore, countryScore));
    }

    private List<HotelIdScore> getHotelIdScores(List<RequestIdPair> requestIdPairs, int hotelScore, int countryScore) {
        List<HotelIdScore> hotelIdScores = Lists.newArrayListWithExpectedSize(requestIdPairs.size());
        for (RequestIdPair requestIdPair : requestIdPairs) {
            hotelIdScores.add(getHotelIdScore(requestIdPair.getIdWith(IdType.HOTEL)
                                                , requestIdPair.getIdWith(IdType.COUNTRY)
                                                , hotelScore, countryScore));
        }

        return hotelIdScores;
    }

    private HotelIdScore getHotelIdScore(int hotelId, int countryId, int hotelScore, int countryScore) {
        boolean isHotelExists = hotelIdRepository.exists(hotelId);
        boolean isCountryExists = countryIdRepository.exists(countryId);

        return new HotelIdScore(hotelId, calculateScore(isHotelExists, hotelScore, isCountryExists, countryScore));
    }

    private double calculateScore(boolean isHotelExists, int hotelScore, boolean isCountryExists, int countryScore) {
        return calculateStrategy.apply(isHotelExists, hotelScore, isCountryExists, countryScore);
    }

    private List<List<RequestIdPair>> divideRequestIdPairs(List<RequestIdPair> requestIdPairs, int size) {
        return Lists.partition(requestIdPairs, size);
    }

    private boolean isEmpty(List<RequestIdPair> requestIdPairs) {
        return CollectionUtils.isEmpty(requestIdPairs);
    }

    private int getDivideSize() {
        return configRepository.getDevideSize();
    }

    private int getCountryScore() {
        return configRepository.getCountryScore();
    }

    private int getHotelScore() {
        return configRepository.getHotelScore();
    }

}
