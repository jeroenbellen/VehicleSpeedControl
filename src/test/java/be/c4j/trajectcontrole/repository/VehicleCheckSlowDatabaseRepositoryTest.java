package be.c4j.trajectcontrole.repository;

import be.c4j.trajectcontrole.dto.Average;
import be.c4j.trajectcontrole.dto.VehicleCheck;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * User: jeroen
 * Date: 6/22/12
 * Time: 11:33 PM
 */
public class VehicleCheckSlowDatabaseRepositoryTest {

    private VehicleCheckRepository vehicleCheckRepository;

    @Before
    public void setUp() throws Exception {
        vehicleCheckRepository = new VehicleCheckSlowDatabaseRepository();
    }

    @Test
    public void shouldGetAllVehicles() throws Exception {
        assertNotNull(vehicleCheckRepository.getAllVehicles());
    }


    @Test
    public void shouldGetAllSpeedingVehicles() throws Exception {
        List<VehicleCheck> speedingVehicles = vehicleCheckRepository.getAllSpeedingVehicles();
        assertNotNull(speedingVehicles);
        for (VehicleCheck vehicleCheck : speedingVehicles) {
            if (vehicleCheck.getAverageSpeed() < VehicleCheckSlowDatabaseRepository.SPEED_LIMIT) {
                fail("Not speeding");
            }
        }
    }

    @Test
    public void shouldGetAverage() throws Exception {
        Average average = vehicleCheckRepository.getAverageSpeed();
        assertNotNull(average);
    }

    @Test
    public void shouldGetSpeedingAverage() throws Exception {
        Average average = vehicleCheckRepository.getSpeedingAverage();
        assertNotNull(average);
    }

    @Test
    public void shouldGetAverageNonSpeeding() throws Exception {
        Average average = vehicleCheckRepository.getNonSpeedingAverage();
        assertNotNull(average);
    }


}
