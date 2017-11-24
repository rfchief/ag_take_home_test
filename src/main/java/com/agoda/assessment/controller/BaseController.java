package com.agoda.assessment.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, String> getInfo(){
        Map<String, String> resultMap = Maps.newHashMap();
        resultMap.put("description", "Agoda Assessment Project");
        return resultMap;
    }

}
