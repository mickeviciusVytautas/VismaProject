package com.visma.fishing.controllers;

import com.visma.fishing.model.Logbook;
import com.visma.fishing.services.LogbookService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.messages.Messages.*;

@Path("/logbook")
public class LogbookController {

    @Inject
    private LogbookService logbookService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLogbook(@Valid Logbook logbook){
        logbookService.create(logbook);
        return Response.status(Response.Status.CREATED).entity(LOGBOOK_SAVE_SUCCESS_MSG).entity(logbook).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getLogbook(@PathParam("id") String id) {
        return logbookService.findById(id)
                .map(logbook -> Response.status(Response.Status.FOUND).entity(logbook).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(LOGBOOK_FIND_FAILED_MSG).build());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogbooks() {
        List<Logbook> logbookList = logbookService.findAll();
        return Response.status(Response.Status.OK).entity(logbookList).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLogbookById(@PathParam("id") String id, Logbook update) {
        Optional<Logbook> optional = logbookService.updateLogbookById(id, update);
        return optional.map(logbook -> Response.status(Response.Status.ACCEPTED).entity(LOGBOOK_UPDATE_SUCCESS_MSG + logbook.getId() + ".").build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(LOGBOOK_FIND_FAILED_MSG + id + ".").build());
    }

    @GET
    @Path("/departure/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Logbook> getLogbooksByDeparturePort(@PathParam("port") String port){
        return logbookService.findByDeparturePort(port);
    }

    @GET
    @Path("/arrival/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Logbook> getLogbooksByArrivalPort(@PathParam("port") String port){
        return logbookService.findByArrivalPort(port);
    }

    @GET
    @Path("/catch/{species}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Logbook> getLogbooksBySpecies(@PathParam("species") String species){
        return logbookService.findBySpecies(species);
    }

    @GET
    @Path("/search/weight/{weight}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Logbook> getCatchesByWeightWithParameter(@PathParam("weight") Long weight, @QueryParam("lower") boolean searchWithLowerWeight) {
        return logbookService.findByWeight(weight, searchWithLowerWeight);
    }

    @DELETE
    @Path("{id}")
    public void deleteLogbookById(@PathParam("id")String id) {
        logbookService.remove(id);
    }

}