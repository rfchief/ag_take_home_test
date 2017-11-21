package com.agoda.assessment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class HotelIdScore implements Serializable{
    private static final long serialVersionUID = 8745460517667140916L;
    private int hotelId;
    private double score;

    public HotelIdScore(int hotelId, double score) {
        this.hotelId = hotelId;
        this.score = score;
    }
}
