package com.agoda.assessment.model;

import com.agoda.assessment.enums.IdType;
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

    public boolean isValid() {
        return hotelId >= 0 || countryId >= 0;
    }

    public boolean isNotValid() {
        return !isValid();
    }

    public int getIdWith(IdType idType) {
        if(idType == IdType.HOTEL)
            return hotelId;

        return countryId;
    }
}