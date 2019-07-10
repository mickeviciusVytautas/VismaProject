package com.visma.fishing.controllers;

import com.visma.fishing.services.DepartureService;
import com.visma.fishing.model.Departure;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/departure")
public class DepartureController {

    @Inject
    DepartureService departureService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDeparture(@Valid Departure departure){
        return departureService.create(departure);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getDeparture(@PathParam("id") String id) {
        return departureService.findById(id)
                .map(departure -> Response.status(Response.Status.FOUND).entity(departure).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Departure by id " + id + " was not found.").build());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartures() {
        List<Departure> departureList = departureService.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        departureList.forEach(a -> jsonArrayBuilder.add(a.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public void deleteDepartureById(@PathParam("id")Long id) {
        departureService.remove(id);
    }

}
