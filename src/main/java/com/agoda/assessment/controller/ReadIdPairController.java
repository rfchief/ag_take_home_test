package com.agoda.assessment.controller;

import com.agoda.assessment.model.HotelIdScore;
import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.service.ReadIdPairService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search/score")
public class ReadIdPairController {

    private final ReadIdPairService readIdPairService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public ReadIdPairController(ReadIdPairService readIdPairService) {
        this.readIdPairService = readIdPairService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(RequestIdPair[].class, new StringToRequestIdPairsBinder());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<HotelIdScore> getHotelIdScores(@RequestParam("ids") RequestIdPair[] requestIdPairs) throws IOException {
        List<HotelIdScore> result = readIdPairService.getScores(Lists.newArrayList(requestIdPairs));
        return result;
    }

    @RequestMapping(value = "/async/get", method = RequestMethod.GET)
    public List<HotelIdScore> asyncGetHotelIdScores(@RequestParam("ids") RequestIdPair[] requestIdPairs) {
        List<HotelIdScore> result = readIdPairService.asyncGetScores(Lists.newArrayList(requestIdPairs));
        return result;
    }
}
