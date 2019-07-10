package com.visma.fishing.controllers;

import com.visma.fishing.model.Arrival;
import com.visma.fishing.services.ArrivalService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
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
    public List<Arrival> getArrivals() {
        return arrivalService.findAll();
    }

    @DELETE
    @Path("{id}")
    public void deleteArrivalById(@PathParam("id")Long id) {
        arrivalService.remove(id);
    }

    @GET
    @Path("/search/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Arrival> findArrivalByPort(@PathParam("port") String port){
        return arrivalService.findByPort(port);
    }

    @GET
    @Path("/search/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Arrival> findArrivalByPeriod(@PathParam("start") String start, @PathParam("end") String end){
        return arrivalService.findByPeriod(start, end);
    }
}