package be.c4j.trajectcontrole.service;

import be.c4j.trajectcontrole.dto.Average;
import be.c4j.trajectcontrole.dto.Total;
import be.c4j.trajectcontrole.dto.VehicleCheck;
import be.c4j.trajectcontrole.repository.VehicleCheckRepository;
import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: jeroen
 * Date: 6/23/12
 * Time: 10:11 AM
 */
@Service
public class SpeedingService implements ISpeedingService {


    private enum MEMCACHED_KEYS {
        TOTAL_TOTAL,
        TOTAL_AVERAGE,
        OFFENDERS_TOTAL,
        OFFENDERS_AVERAGE,
        OFFENDERS_LIST,
        NONOFFENDERS_TOTAL,
        NONOFFENDERS_AVERAGE;


    }

    @Autowired
    private VehicleCheckRepository slowDatabaseRepository;
    @Autowired
    private MemcachedClient memcachedClient;


    // Non - Deterministic caching examples

    @Override
    public Total getTotal() {
        Total total;
        try {
            total = memcachedClient.get(MEMCACHED_KEYS.TOTAL_TOTAL.toString());   // Get value out from memcached
            if (total == null) {    // If total is not in the cache, get if from the database and cache it
                total = new Total((long) slowDatabaseRepository.getAllVehicles().size());
                memcachedClient.set(MEMCACHED_KEYS.TOTAL_TOTAL.toString(), 0, total);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return total;
    }

    @Override
    public List<VehicleCheck> getAllSpeedingVehicles() {
        // TODO 1: Implement Non-Deterministic cache
        return slowDatabaseRepository.getAllSpeedingVehicles();
    }

    @Override
    public Average getAverageSpeed() {
        // TODO 2: Implement Non-Deterministic cache
        return slowDatabaseRepository.getAverageSpeed();
    }

    @Override
    public Average getSpeedingAverage() {
        // TODO 3: Implement Non-Deterministic cache
        return slowDatabaseRepository.getSpeedingAverage();
    }

    @Override
    public Total getTotalOffenders() {
        // TODO 4: Implement Non-Deterministic cache
        return new Total((long) slowDatabaseRepository.getAllSpeedingVehicles().size());
    }


    // Non - Deterministic caching examples

    @Scheduled(cron = "0 0 * * 0")  // Weekly job --> Sunday at midnight
    //TODO 5: Uncomment @PostConstruct                  // Run this method on startup
    @Async
    public void buildDeterministicCache() {
        final List<VehicleCheck> vehicles = slowDatabaseRepository.getAllVehicles();
        vehicles.removeAll(slowDatabaseRepository.getAllSpeedingVehicles());
        Total total = new Total((long) vehicles.size());

        try {
            memcachedClient.set(MEMCACHED_KEYS.NONOFFENDERS_TOTAL.toString(), 0, total);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // Deterministic cache
    public Total getTotalNonOffenders() {
        try {
            return memcachedClient.get(MEMCACHED_KEYS.NONOFFENDERS_TOTAL.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Average getNonSpeedingAverage() {
        // TODO 6: Implement Deterministic cache
        return slowDatabaseRepository.getNonSpeedingAverage();
    }
}
