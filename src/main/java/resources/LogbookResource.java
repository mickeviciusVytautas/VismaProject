package resources;

import EJB.LogbookEJB;
import EJB.LogbookEJBImpl;
import model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/logbook")
public class LogbookResource {

    @Inject
    LogbookEJB logbookEJB;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLogbook(Logbook logbook){
        logbookEJB.create(logbook);

        return Response.ok(logbook.toJson()).build();
    }
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
        List<Logbook> logbookList = logbookEJB.findAll();

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        logbookList.forEach(l -> jsonArrayBuilder.add(l.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }


    @DELETE
    @Path("{id}")
    public Response deleteLogbookById(@PathParam("id")Long id) {
        logbookEJB.remove(id);
        return Response.ok().build();
    }
}