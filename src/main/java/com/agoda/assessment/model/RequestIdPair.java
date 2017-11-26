package com.agoda.assessment.model;

import com.agoda.assessment.model.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RequestIdPair implements Serializable {
    private static final long serialVersionUID = 6886743803574100477L;

    private int hotelId;
    private int countryId;

    public RequestIdPair(int hotelId, int countryId) {
        this.hotelId = hotelId;
        this.countryId = countryId;
    }

    @JsonIgnore
    public boolean isValid() {
        return (hotelId >= 0 && hotelId <= Integer.MAX_VALUE)
                && (countryId >= 0 && countryId <= Integer.MAX_VALUE);
    }

    @JsonIgnore
    public boolean isNotValid() {
        return !isValid();
    }

    @JsonIgnore
    public int getIdWith(IdType idType) {
        if(idType == IdType.HOTEL)
            return hotelId;

        return countryId;
    }
}
