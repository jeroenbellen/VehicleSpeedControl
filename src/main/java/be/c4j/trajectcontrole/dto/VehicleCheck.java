package be.c4j.trajectcontrole.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * User: jeroen
 * Date: 6/22/12
 * Time: 10:38 PM
 */
public class VehicleCheck implements Serializable {
    private String licencePlate;
    private Double averageSpeed;
    private Date dateTime;

    public VehicleCheck() {
        licencePlate = "";
        averageSpeed = 0.0;
        dateTime = new Date();
    }


    public VehicleCheck licencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
        return this;
    }

    public VehicleCheck averageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
        return this;
    }

    public VehicleCheck dateTime(Date dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleCheck that = (VehicleCheck) o;

        if (!averageSpeed.equals(that.averageSpeed)) return false;
        if (!dateTime.equals(that.dateTime)) return false;
        if (!licencePlate.equals(that.licencePlate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = licencePlate.hashCode();
        result = 31 * result + averageSpeed.hashCode();
        result = 31 * result + dateTime.hashCode();
        return result;
    }
}
