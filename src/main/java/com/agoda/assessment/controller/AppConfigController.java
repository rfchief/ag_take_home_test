package com.agoda.assessment.controller;

import com.agoda.assessment.service.AppConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/config")
public class AppConfigController {

    private final AppConfigService appConfigService;

    @Autowired
    public AppConfigController(AppConfigService appConfigService) {
        this.appConfigService = appConfigService;
    }

    @RequestMapping(value = "/divide", method = {RequestMethod.POST, RequestMethod.PUT})
    public boolean updateDivideSize(@RequestParam("size") int divideSize) {
        return appConfigService.updateDivideSize(divideSize);
    }

    @RequestMapping(value = "/score/hotel", method = {RequestMethod.POST, RequestMethod.PUT})
    public boolean updateHotelScore(@RequestParam("score") int hotelScore) {
        return appConfigService.updateHotelScore(hotelScore);
    }

    @RequestMapping(value = "/score/country", method = {RequestMethod.POST, RequestMethod.PUT})
    public boolean updateCountryScore(@RequestParam("score") int countryScore) {
        return appConfigService.updateCountryScore(countryScore);
    }
}
