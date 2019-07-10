package com.visma.fishing.controllers;

import com.visma.fishing.model.Arrival;
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
        return departureService.findById(id);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departure> getDepartures() {
        return departureService.findAll();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDepartureById(@PathParam("id") String id, Departure departure){
        return departureService.update(id, departure);
    }

    @DELETE
    @Path("{id}")
    public void deleteDepartureById(@PathParam("id")String id) {
        departureService.remove(id);
    }

    @GET
    @Path("/search/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departure> findDepartureByPort(@PathParam("port") String port){
        return departureService.findByPort(port);
    }

    @GET
    @Path("/search/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departure> findDepartureByPeriod(@PathParam("start") String start, @PathParam("end") String end){
        return departureService.findByPeriod(start, end);
    }

}
