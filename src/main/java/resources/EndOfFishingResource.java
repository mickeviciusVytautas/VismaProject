package resources;

import EJB.EndOfFishingEJB;
import model.EndOfFishing;
import model.Logbook;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/endoffishing")
public class EndOfFishingResource {
    @Inject
    EndOfFishingEJB endOfFishingEjb;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEndOfFishing(EndOfFishing endOfFishing){
        endOfFishingEjb.create(endOfFishing);
        return Response.ok(endOfFishing.toJson()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getEndOfFishing(@PathParam("id") Long id) {
        EndOfFishing endOfFishing = endOfFishingEjb.findById(id);
        return Response.ok(endOfFishing.toJson())
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEndOfFishings() {
        List<EndOfFishing> endOfFishingList = endOfFishingEjb.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        endOfFishingList.forEach(l -> jsonArrayBuilder.add(l.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEndOfFishing(@PathParam("id")Long id) {
        endOfFishingEjb.remove(id);
        return Response.ok().build();
    }
}
