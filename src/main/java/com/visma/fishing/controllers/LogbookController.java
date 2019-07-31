package com.visma.fishing.controllers;

import com.visma.fishing.exception.ConcurrentChangesException;
import com.visma.fishing.exception.EntityNotFoundException;
import com.visma.fishing.model.Logbook;
import com.visma.fishing.services.LogbookService;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.visma.fishing.messages.Messages.LOGBOOK_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.LOGBOOK_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.LOGBOOK_UPDATE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.format;

@Path("/logbook")
public class LogbookController {

    @Inject
    private LogbookService logbookService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLogbook(@Valid Logbook logbook) {
        logbookService.create(logbook);
        return Response.status(Response.Status.CREATED).entity(LOGBOOK_SAVE_SUCCESS_MSG).build();
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
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLogbookById(Logbook logbook) {
        try {
            logbookService.updateLogbook(logbook);
            return Response.status(Response.Status.ACCEPTED).entity(format(LOGBOOK_UPDATE_SUCCESS_MSG, logbook.getId())).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ConcurrentChangesException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/departure/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Logbook> getLogbooksByDeparturePort(@PathParam("port") String port) {
        return logbookService.findByDeparturePort(port);
    }

    @GET
    @Path("/arrival/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Logbook> getLogbooksByArrivalPort(@PathParam("port") String port) {
        return logbookService.findByArrivalPort(port);
    }

    @GET
    @Path("/catch/{species}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Logbook> getLogbooksBySpecies(@PathParam("species") String species) {
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
    public void deleteLogbookById(@PathParam("id") String id) {
        logbookService.remove(id);
    }

}