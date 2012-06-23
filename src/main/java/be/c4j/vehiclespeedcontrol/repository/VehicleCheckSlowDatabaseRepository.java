package be.c4j.vehiclespeedcontrol.repository;

import be.c4j.vehiclespeedcontrol.dto.Average;
import be.c4j.vehiclespeedcontrol.dto.VehicleCheck;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: jeroen
 * Date: 6/22/12
 * Time: 11:04 PM
 */
@Repository
public class VehicleCheckSlowDatabaseRepository implements VehicleCheckRepository {

    public static final double SPEED_LIMIT = 96.00D;

    @Override
    public List<VehicleCheck> getAllVehicles() {
        doSomethingSlowLikeTransactions();
        return readAllVehicles();
    }

    @Override
    public List<VehicleCheck> getAllSpeedingVehicles() {
        final ArrayList<VehicleCheck> speeding = new ArrayList<VehicleCheck>();
        for (VehicleCheck vehicleCheck : getAllVehicles()) {
            if (vehicleCheck.getAverageSpeed() >= SPEED_LIMIT) {
                speeding.add(vehicleCheck);
            }
        }
        return speeding;
    }

    @Override
    public Average getAverageSpeed() {
        return calculateAverage(getAllVehicles());
    }

    @Override
    public Average getSpeedingAverage() {
        return calculateAverage(getAllSpeedingVehicles());
    }

    @Override
    public Average getNonSpeedingAverage() {
        final List<VehicleCheck> vehicles = getAllVehicles();
        vehicles.removeAll(getAllSpeedingVehicles());
        return calculateAverage(vehicles);
    }

    private Average calculateAverage(List<VehicleCheck> vehicles) {
        Double average = 0.0D;
        for (VehicleCheck vehicleCheck : vehicles) {
            average += vehicleCheck.getAverageSpeed();
        }
        average /= vehicles.size();
        return new Average(average);
    }

    private void doSomethingSlowLikeTransactions() {
        try {
            Thread.sleep(new Random(System.currentTimeMillis()).nextInt(4000) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<VehicleCheck> readAllVehicles() {
        final ArrayList<VehicleCheck> vehicleChecks = new ArrayList<VehicleCheck>();
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            inputStreamReader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("vehicleCheck.csv"));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                line = bufferedReader.readLine();
                if (line != null) {
                    final String[] split = line.split(",");   //
                    vehicleChecks.add(
                            new VehicleCheck()
                                    .licencePlate(split[0])
                                    .averageSpeed(Double.parseDouble(split[1]))
                                    .dateTime(simpleDateFormat.parse(split[2]))
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return vehicleChecks;
    }
}
