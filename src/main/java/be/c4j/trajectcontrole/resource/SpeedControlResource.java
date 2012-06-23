package be.c4j.trajectcontrole.resource;

import be.c4j.trajectcontrole.dto.Average;
import be.c4j.trajectcontrole.dto.Total;
import be.c4j.trajectcontrole.dto.VehicleCheck;
import be.c4j.trajectcontrole.service.ISpeedingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * User: jeroen
 * Date: 6/22/12
 * Time: 9:15 PM
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Path("/speedcontrol")
public class SpeedControlResource {


    @Autowired
    private ISpeedingService speedingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/average")
    public Average average() {
        return speedingService.getAverageSpeed();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offenders/average")
    public Average offendersAverage() {
        return speedingService.getSpeedingAverage();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/nonoffenders/average")
    public Average nonOffendersAverage() {
        return speedingService.getNonSpeedingAverage();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total")
    public Total total() {
        return speedingService.getTotal();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offenders/total")
    public Total offendersTotal() {
        return speedingService.getTotalOffenders();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/nonoffenders/total")
    public Total nonOffendersTotal() {
        return speedingService.getTotalNonOffenders();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offenders/list")
    public List<VehicleCheck> offendersList() {
        return speedingService.getAllSpeedingVehicles();
    }


}
