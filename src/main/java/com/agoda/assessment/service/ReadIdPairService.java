package com.agoda.assessment.service;

import com.agoda.assessment.enums.IdType;
import com.agoda.assessment.model.HotelIdScore;
import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.repository.ConfigRepository;
import com.agoda.assessment.repository.IdRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ReadIdPairService {

    private final IdRepository hotelIdRepository;
    private final IdRepository countryIdRepository;
    private final ConfigRepository configRepository;

    @Autowired
    public ReadIdPairService(@Qualifier("hotelIdRepository") IdRepository hotelIdRepository,
                             @Qualifier("countryIdRepository") IdRepository countryIdRepository,
                             @Qualifier("configRepository") ConfigRepository configRepository) {
        this.hotelIdRepository = hotelIdRepository;
        this.countryIdRepository = countryIdRepository;
        this.configRepository = configRepository;
    }

    public List<HotelIdScore> read(List<RequestIdPair> requestIdPairs) {
        if(CollectionUtils.isEmpty(requestIdPairs))
            return Lists.newArrayList();

        return getHotelIdScores(requestIdPairs);
    }

    public List<HotelIdScore> readByAsync(List<RequestIdPair> requestIdPairs) {
        if(CollectionUtils.isEmpty(requestIdPairs))
            return Lists.newArrayList();

        List<List<RequestIdPair>> dividedRequests = divideRequestIdPairs(requestIdPairs, getDividedSize());
        List<CompletableFuture> completableFutures = Lists.newArrayListWithExpectedSize(dividedRequests.size());
        for (List<RequestIdPair> dividedRequest : dividedRequests)
            completableFutures.add(getHotelIdScoresByAsync(dividedRequest));

        return Lists.newArrayList();
    }

    @Async
    private CompletableFuture<List<HotelIdScore>> getHotelIdScoresByAsync(List<RequestIdPair> dividedRequest) {
        return CompletableFuture.completedFuture(getHotelIdScores(dividedRequest));
    }

    private List<HotelIdScore> getHotelIdScores(List<RequestIdPair> requestIdPairs) {
        List<HotelIdScore> hotelIdScores = Lists.newArrayListWithExpectedSize(requestIdPairs.size());
        for (RequestIdPair requestIdPair : requestIdPairs) {
            hotelIdScores.add(getHotelIdScore(requestIdPair.getIdWith(IdType.HOTEL)
                                                , requestIdPair.getIdWith(IdType.COUNTRY)));
        }

        return hotelIdScores;
    }

    private HotelIdScore getHotelIdScore(int hotelId, int countryId) {
        boolean isHotelExists = hotelIdRepository.exists(hotelId);
        boolean isCountryExists = countryIdRepository.exists(countryId);

        return new HotelIdScore(hotelId, calculateScore(isHotelExists, isCountryExists));
    }

    private List<List<RequestIdPair>> divideRequestIdPairs(List<RequestIdPair> requestIdPairs, int size) {
        return Lists.partition(requestIdPairs, size);
    }

    private double calculateScore(boolean isHotelExists, boolean isCountryExists) {
        return 0;
    }

    private int getDividedSize() {
        return configRepository.getInt("dividedSize");
    }
}
