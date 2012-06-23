package be.c4j.trajectcontrole.dto;

import java.io.Serializable;

/**
 * User: jeroen
 * Date: 6/22/12
 * Time: 9:24 PM
 */
public class Average implements Serializable {
    private Double average;

    public Average() {
    }

    public Average(Double average) {
        this.average = average;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
