package com.visma.fishing.controllers;

import com.visma.fishing.services.LogbookService;
import com.visma.fishing.model.Logbook;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/logbook")
public class LogbookController {

    @Inject
    LogbookService logbookService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLogbook(@Valid Logbook logbook){
        return logbookService.create(logbook);
    }

    @POST
    @Path("/satellite")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLogbookToFileSystem(@Valid Logbook logbook){
        return logbookService.createBySatellite(logbook);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getLogbook(@PathParam("id") Long id) {
        return logbookService.findById(id)
                .map(logbook -> Response.status(Response.Status.FOUND).entity(logbook).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Logbook by id " + id + " was not found.").build());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogbooks() {
        List<Logbook> logbookList = logbookService.findAll();
        return Response.ok(logbookList).build();
    }

    @GET
    @Path("/port")
    public Response getLogbooksByDeparturePort(String portName){
        logbookService.findByDeparturePort(portName);
        return null;
    }

    @DELETE
    @Path("{id}")
    public void deleteLogbookById(@PathParam("id")Long id) {
        logbookService.remove(id);
    }

}