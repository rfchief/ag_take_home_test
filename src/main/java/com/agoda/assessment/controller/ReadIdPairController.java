package com.agoda.assessment.controller;

import com.agoda.assessment.model.HotelIdScore;
import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.service.ReadIdPairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search/score")
public class ReadIdPairController {

    private final ReadIdPairService readIdPairService;

    @Autowired
    public ReadIdPairController(ReadIdPairService readIdPairService) {
        this.readIdPairService = readIdPairService;
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    public List<HotelIdScore> getHotelIdScores(@RequestParam("requestIds") List<RequestIdPair> requestIdPairs) {
        List<HotelIdScore> result = readIdPairService.getScores(requestIdPairs);
        return result;
    }

    @RequestMapping(value = "/async/get", method = {RequestMethod.GET, RequestMethod.POST})
    public List<HotelIdScore> asyncGetHotelIdScores(@RequestParam("requestIds") List<RequestIdPair> requestIdPairs) {
        List<HotelIdScore> result = readIdPairService.asyncGetScores(requestIdPairs);
        return result;
    }
}
