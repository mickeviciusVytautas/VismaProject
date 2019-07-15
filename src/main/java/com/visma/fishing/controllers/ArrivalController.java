package com.visma.fishing.controllers;

import com.visma.fishing.model.Arrival;
import com.visma.fishing.services.ArrivalService;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.visma.fishing.auxiliary.Messages.*;

@Path("/arrival")
public class ArrivalController {

    @Inject
    private ArrivalService arrivalService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArrival(@Valid Arrival arrival){
        return Response.status(Response.Status.CREATED).entity(ARRIVAL_SAVE_SUCCESS_MSG).entity(arrival).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getArrival(@PathParam("id") String id) {
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
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateArrivalById(@PathParam("id") String id, Arrival arrival){
        return arrivalService.updateArrivalById(id, arrival)
                .map(configuration -> Response.status(Response.Status.ACCEPTED).entity(ARRIVAL_UPDATE_SUCCESS_MSG + id + ".").build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(ARRIVAL_FIND_FAILED_MSG + id + ".").build());
    }

    @DELETE
    @Path("{id}")
    public void deleteArrivalById(@PathParam("id")String id) {
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
        try {
            Date startDate = DateUtil.parseDate(start);
            Date endDate = DateUtil.parseDate(end);
            return arrivalService.findByPeriod(startDate, endDate);
        } catch (DateParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
