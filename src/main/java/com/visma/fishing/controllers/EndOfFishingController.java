package com.visma.fishing.controllers;

import com.visma.fishing.exception.ConcurrentChangesException;
import com.visma.fishing.exception.EntityNotFoundException;
import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.services.EndOfFishingService;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.visma.fishing.messages.Messages.END_OF_FISHING_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.END_OF_FISHING_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.END_OF_FISHING_UPDATE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.format;

@Path("/endoffishing")
public class EndOfFishingController {

    @Inject
    private EndOfFishingService endOfFishingService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEndOfFishing(@Valid EndOfFishing endOfFishing) {
        endOfFishingService.create(endOfFishing);
        return Response.status(Response.Status.CREATED).entity(END_OF_FISHING_SAVE_SUCCESS_MSG).entity(endOfFishing).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getEndOfFishing(@PathParam("id") Long id) {
        return endOfFishingService.findById(id)
                .map(endOfFishing -> Response.status(Response.Status.FOUND).entity(endOfFishing).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(END_OF_FISHING_FIND_FAILED_MSG + id + ".").build());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EndOfFishing> getEndOfFishingList() {
        return endOfFishingService.findAll();
    }

    @DELETE
    @Path("{id}")
    public void deleteEndOfFishing(@PathParam("id") Long id) {
        endOfFishingService.remove(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEndOfFishing(EndOfFishing endOfFishing) {
        try {
            endOfFishingService.updateEndOfFishing(endOfFishing);
            return Response.status(Response.Status.ACCEPTED)
                    .entity(format(END_OF_FISHING_UPDATE_SUCCESS_MSG, endOfFishing.getId()))
                    .build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ConcurrentChangesException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EndOfFishing> findEndOfFishingByPeriod(@PathParam("start") String start, @PathParam("end") String end) {
        return endOfFishingService.findByPeriod(start, end);
    }
}
