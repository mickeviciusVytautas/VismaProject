package com.visma.fishing.controllers;

import com.visma.fishing.model.Departure;
import com.visma.fishing.services.DepartureService;

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

import static com.visma.fishing.messages.Messages.DEPARTURE_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.DEPARTURE_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.DEPARTURE_UPDATE_SUCCESS_MSG;

@Path("/departure")
public class DepartureController {

    @Inject
    private DepartureService departureService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDeparture(@Valid Departure departure) {
        departureService.create(departure);
        return Response.status(Response.Status.CREATED).entity(DEPARTURE_SAVE_SUCCESS_MSG).entity(departure).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getDeparture(@PathParam("id") String id) {
        return departureService.findById(id).map(
                departure -> Response.status(Response.Status.FOUND).entity(departure).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(DEPARTURE_FIND_FAILED_MSG + id + ".").build());
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
    public Response updateDepartureById(@PathParam("id") String id, Departure update) {
        return departureService.updateDepartureById(id, update)
                .map(departure -> Response.status(Response.Status.ACCEPTED).entity(DEPARTURE_UPDATE_SUCCESS_MSG + update.getId() + ".").build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(DEPARTURE_FIND_FAILED_MSG + id + ".").build());
    }

    @DELETE
    @Path("{id}")
    public void deleteDepartureById(@PathParam("id") String id) {
        departureService.remove(id);
    }

    @GET
    @Path("/search/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departure> findDepartureByPort(@PathParam("port") String port) {
        return departureService.findByPort(port);
    }

    @GET
    @Path("/search/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departure> findDepartureByPeriod(@PathParam("start") String start, @PathParam("end") String end) {
        return departureService.findByPeriod(start, end);
    }

}
