package be.c4j.vehiclespeedcontrol.service;

import be.c4j.vehiclespeedcontrol.dto.Average;
import be.c4j.vehiclespeedcontrol.dto.Total;
import be.c4j.vehiclespeedcontrol.dto.VehicleCheck;

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
