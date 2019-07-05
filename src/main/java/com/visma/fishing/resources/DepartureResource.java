package com.visma.fishing.resources;

import com.visma.fishing.EJB.DepartureEJB;
import com.visma.fishing.model.Departure;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class DepartureResource {

    @Inject
    DepartureEJB departureEJB;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDeparture(Departure departure){
        departureEJB.create(departure);
        return Response.ok(departure.toJson()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getDeparture(@PathParam("id") Long id) {
        Departure departure = departureEJB.findById(id);
        return Response.ok(departure)
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartures() {
        List<Departure> departureList = departureEJB.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        departureList.forEach(a -> jsonArrayBuilder.add(a.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDepartureById(@PathParam("id")Long id) {
        departureEJB.remove(id);
        return Response.ok().build();
    }

}
