package com.agoda.assessment.controller;

import com.agoda.assessment.model.RequestIdPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

@Component
public class StringToRequestIdPairsBinder extends PropertyEditorSupport {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text))
            throw new IllegalArgumentException();

        try {
            setValue(objectMapper.readValue(text, RequestIdPair[].class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid json string value!!");
        }
    }

    @Override
    public String getAsText() {
        try {
            return objectMapper.writeValueAsString(this.getValue());
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

}
