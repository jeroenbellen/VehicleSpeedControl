package be.c4j.vehiclespeedcontrol.repository;

import be.c4j.vehiclespeedcontrol.dto.Average;
import be.c4j.vehiclespeedcontrol.dto.VehicleCheck;

import java.util.List;

/**
 * User: jeroen
 * Date: 6/22/12
 * Time: 10:59 PM
 */
public interface VehicleCheckRepository {
    List<VehicleCheck> getAllVehicles();

    List<VehicleCheck> getAllSpeedingVehicles();

    Average getAverageSpeed();

    Average getSpeedingAverage();

    Average getNonSpeedingAverage();
}
