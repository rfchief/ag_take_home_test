package com.agoda.assessment.controller;

import com.agoda.assessment.service.WriteIdPairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WriteIdPairController {

    private final WriteIdPairService writeIdPairService;

    @Autowired
    public WriteIdPairController(WriteIdPairService writeIdPairService) {
        this.writeIdPairService = writeIdPairService;
    }
}
