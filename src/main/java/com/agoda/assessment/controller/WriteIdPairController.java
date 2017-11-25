package com.agoda.assessment.controller;

import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.service.WriteIdPairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/repository")
public class WriteIdPairController {

    private final WriteIdPairService writeIdPairService;

    @Autowired
    public WriteIdPairController(WriteIdPairService writeIdPairService) {
        this.writeIdPairService = writeIdPairService;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateHotelAndCountryIds(@RequestBody List<RequestIdPair> idPairs) {
        writeIdPairService.write(idPairs);
    }
}
