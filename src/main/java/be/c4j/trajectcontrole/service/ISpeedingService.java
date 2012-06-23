package be.c4j.trajectcontrole.service;

import be.c4j.trajectcontrole.dto.Average;
import be.c4j.trajectcontrole.dto.Total;
import be.c4j.trajectcontrole.dto.VehicleCheck;

import java.util.List;

/**
 * User: jeroen
 * Date: 6/23/12
 * Time: 10:10 AM
 */
public interface ISpeedingService {

    List<VehicleCheck> getAllSpeedingVehicles();

    Average getAverageSpeed();

    Average getSpeedingAverage();

    Average getNonSpeedingAverage();

    Total getTotal();

    Total getTotalOffenders();

    Total getTotalNonOffenders();
}
