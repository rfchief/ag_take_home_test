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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    public List<HotelIdScore> asyncRead(List<RequestIdPair> requestIdPairs) {
        if(isEmpty(requestIdPairs))
            return Lists.newArrayList();

        return asyncGetHotelIdScores(requestIdPairs);
    }

    private List<HotelIdScore> asyncGetHotelIdScores(List<RequestIdPair> requestIdPairs) {
        List<CompletableFuture<List<HotelIdScore>>> futures = getFutureHotelIdScores(requestIdPairs, getHotelScore(), getCountryScore());
        return getResultFrom(futures, requestIdPairs.size());
    }

    private List<CompletableFuture<List<HotelIdScore>>> getFutureHotelIdScores(List<RequestIdPair> requestIdPairs, int hotelScore, int countryScore) {
        List<List<RequestIdPair>> dividedRequests = divideRequestIdPairs(requestIdPairs, getDivideSize());
        List<CompletableFuture<List<HotelIdScore>>> completableFutures = Lists.newArrayListWithExpectedSize(dividedRequests.size());

        for (List<RequestIdPair> dividedRequest : dividedRequests)
            completableFutures.add(getHotelIdScoresByAsync(dividedRequest, hotelScore, countryScore));

        return completableFutures;
    }

    @Async
    private CompletableFuture<List<HotelIdScore>> getHotelIdScoresByAsync(List<RequestIdPair> dividedRequest, int hotelScore, int countryScore) {
        return CompletableFuture.completedFuture(getHotelIdScores(dividedRequest, hotelScore, countryScore));
    }

    private List<HotelIdScore> getHotelIdScores(List<RequestIdPair> requestIdPairs, int hotelScore, int countryScore) {
        List<HotelIdScore> hotelIdScores = Lists.newArrayListWithExpectedSize(requestIdPairs.size());

        for (RequestIdPair requestIdPair : requestIdPairs) {
            int hotelId = requestIdPair.getIdWith(IdType.HOTEL);
            int countryId = requestIdPair.getIdWith(IdType.COUNTRY);

            hotelIdScores.add(getHotelIdScore(hotelId, countryId, hotelScore, countryScore));
        }

        return hotelIdScores;
    }

    private List<HotelIdScore> getResultFrom(List<CompletableFuture<List<HotelIdScore>>> futures, int expectedResultSize) {
        int completeCount = 0;
        List<HotelIdScore> hotelIdScores = Lists.newArrayListWithExpectedSize(expectedResultSize);

        for (CompletableFuture<List<HotelIdScore>> future : futures) {
            if(future.isDone()) {
                try {
                    List<HotelIdScore> actual = future.get(1, TimeUnit.SECONDS);
                    hotelIdScores.addAll(actual);
                } catch (InterruptedException e) {
                    log.error("Failed to get HotelIdScores. [ Message : {}]", e.getMessage());
                } catch (ExecutionException e) {
                    log.error("Failed to get HotelIdScores. [ Message : {}]", e.getMessage());
                } catch (TimeoutException e) {
                    log.error("Failed to get HotelIdScores. [ Message : {}]", e.getMessage());
                }

                completeCount++;
            }

            if(completeCount == futures.size())
                break;
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
        return configRepository.getDivideSize();
    }

    private int getCountryScore() {
        return configRepository.getCountryScore();
    }

    private int getHotelScore() {
        return configRepository.getHotelScore();
    }

}
