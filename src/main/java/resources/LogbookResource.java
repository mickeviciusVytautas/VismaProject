package resources;

import model.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/logbook")
public class LogbookResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogbook() {
        Departure departure = new Departure(new Date(), "port");
        Catch aCatch = new Catch("afrikines silkes", 1L);
        Arrival arrival = new Arrival("amazing port", new Date());
        EndOfFishing endOfFishing = new EndOfFishing(new Date());
        Logbook logbook =  new Logbook(departure, aCatch, arrival, endOfFishing);
        return Response.ok(logbook.toJson())
                .header("hello", "world")
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogbooks() {
        Departure departure = new Departure(new Date(), "port");
        Catch aCatch = new Catch("afrikines silkes", 1L);
        Arrival arrival = new Arrival("amazing port", new Date());
        EndOfFishing endOfFishing = new EndOfFishing(new Date());
        Logbook logbook =  new Logbook(departure, aCatch, arrival, endOfFishing);
        JsonArray jsonArray = Json.createArrayBuilder().add(logbook.toJson()).add(logbook.toJson()).build();
        return Response.ok(jsonArray)
                .header("hello", "world")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLogbook(Logbook logbook){
        return Response.ok(logbook.toJson()).build();
    }
}