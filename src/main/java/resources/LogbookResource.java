package resources;

import model.*;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.Date;

@Path("/logbook")
public class LogbookResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getLogbooks() {
        Departure departure = new Departure(new Date(), "port");
        Catch aCatch = new Catch("afrikines silkes", 1L);
        Arrival arrival = new Arrival("amazing port", new Date());
        EndOfFishing endOfFishing = new EndOfFishing(new Date());
        Logbook logbook =  new Logbook(departure, aCatch, arrival, endOfFishing);
        return logbook.toJson();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Logbook createLogbook(Logbook logbook){
        return logbook;
    }
}