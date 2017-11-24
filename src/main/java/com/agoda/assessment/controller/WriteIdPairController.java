package com.agoda.assessment.controller;

import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.service.WriteIdPairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/id")
public class WriteIdPairController {

    private final WriteIdPairService writeIdPairService;

    @Autowired
    public WriteIdPairController(WriteIdPairService writeIdPairService) {
        this.writeIdPairService = writeIdPairService;
    }

    @RequestMapping(value = "/replace", method = {RequestMethod.POST, RequestMethod.PUT})
    public String updateHotelAndCountryIds(@RequestParam("replaceIds") List<RequestIdPair> idPairs) {
        writeIdPairService.write(idPairs);

        return "OK!";
    }
}
