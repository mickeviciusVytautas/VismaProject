package com.visma.fishing.controllers;

import com.visma.fishing.exception.ConcurrentChangesException;
import com.visma.fishing.exception.EntityNotFoundException;
import com.visma.fishing.model.Arrival;
import com.visma.fishing.services.ArrivalService;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.visma.fishing.messages.Messages.ARRIVAL_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.ARRIVAL_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.ARRIVAL_UPDATE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.format;

@Path("/arrival")
public class ArrivalController {

    private final Logger log = LogManager.getLogger(ArrivalController.class);

    @Inject
    private ArrivalService arrivalService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArrival(@Valid Arrival arrival) {
        return Response.status(Response.Status.CREATED).entity(ARRIVAL_SAVE_SUCCESS_MSG).entity(arrival).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getArrival(@PathParam("id") Long id) {
        return arrivalService.findById(id)
                .map(arrival -> Response.status(Response.Status.FOUND).entity(arrival).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(ARRIVAL_FIND_FAILED_MSG + id + ".").build());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Arrival> getArrivals() {
        return arrivalService.findAll();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateArrivalById(Arrival arrival) {
        try {
            arrivalService.updateArrival(arrival);
            return Response.status(Response.Status.ACCEPTED).entity(format(ARRIVAL_UPDATE_SUCCESS_MSG, arrival.getId())).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ConcurrentChangesException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteArrivalById(@PathParam("id") Long id) {
        arrivalService.remove(id);
    }

    @GET
    @Path("/search/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Arrival> findArrivalByPort(@PathParam("port") String port) {
        return arrivalService.findByPort(port);
    }

    @GET
    @Path("/search/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Arrival> findArrivalByPeriod(@PathParam("start") String start, @PathParam("end") String end) {
        try {
            Date startDate = DateUtil.parseDate(start);
            Date endDate = DateUtil.parseDate(end);
            return arrivalService.findByPeriod(startDate, endDate);
        } catch (DateParseException e) {
            log.error(e);
        }
        return new ArrayList<>();
    }
}
