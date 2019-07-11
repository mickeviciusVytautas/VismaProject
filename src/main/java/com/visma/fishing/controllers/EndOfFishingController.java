package com.visma.fishing.controllers;

import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.services.EndOfFishingService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/endoffishing")
public class EndOfFishingController {

    @Inject
    EndOfFishingService endOfFishingService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEndOfFishing(@Valid EndOfFishing endOfFishing){
        return endOfFishingService.create(endOfFishing);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getEndOfFishing(@PathParam("id") String id) {
        return endOfFishingService.findById(id);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEndOfFishingList() {
        List<EndOfFishing> endOfFishingList = endOfFishingService.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        endOfFishingList.forEach(l -> jsonArrayBuilder.add(l.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEndOfFishing(@PathParam("id")String id) {
        endOfFishingService.remove(id);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEndOfFishing(@PathParam("id") String id, EndOfFishing endOfFishing){
        return endOfFishingService.update(id, endOfFishing);
    }

    @GET
    @Path("/search/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EndOfFishing> findEndOfFishingByPeriod(@PathParam("start") String start, @PathParam("end") String end){
        return endOfFishingService.findByPeriod(start, end);
    }
}
