package com.visma.fishing.controllers;

import com.visma.fishing.services.ArrivalService;
import com.visma.fishing.model.Arrival;


import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.ok;

@Path("/arrival")
public class ArrivalController {

    @Inject
    ArrivalService arrivalService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArrival(@Valid Arrival arrival){
        return arrivalService.create(arrival);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getArrival(@PathParam("id") String id) {
        return arrivalService.findById(id)
                .map(arrival -> Response.status(Response.Status.FOUND).entity(arrival).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Arrival by id " + id + " was not found.").build());

    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArrivals() {
        List<Arrival> arrivalList = arrivalService.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        arrivalList.forEach(a -> jsonArrayBuilder.add(a.toJson()));
        return ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteArrivalById(@PathParam("id")Long id) {
        arrivalService.remove(id);
        return ok().build();
    }

}
