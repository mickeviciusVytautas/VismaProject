package com.visma.fishing.resources;

import com.visma.fishing.EJB.ArrivalEJB;
import com.visma.fishing.model.Arrival;


import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/arrival")
public class ArrivalResource {

    @Inject
    ArrivalEJB arrivalEJB;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArrival(Arrival arrival){
        arrivalEJB.create(arrival);
        return Response.ok(arrival.toJson()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getArrival(@PathParam("id") Long id) {
        Arrival arrival = arrivalEJB.findById(id);
        return Response.ok(arrival)
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArrivals() {
        List<Arrival> arrivalList = arrivalEJB.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        arrivalList.forEach(a -> jsonArrayBuilder.add(a.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteArrivalById(@PathParam("id")Long id) {
        arrivalEJB.remove(id);
        return Response.ok().build();
    }

}
