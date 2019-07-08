package com.visma.fishing.controllers;

import com.visma.fishing.services.EndOfFishingService;
import com.visma.fishing.model.EndOfFishing;

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
    public Response getEndOfFishing(@PathParam("id") Long id) {
        return endOfFishingService.findById(id)
                .map(endOfFishing -> Response.status(Response.Status.FOUND).entity(endOfFishing).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("EndOfFishing by id " + id + " was not found.").build());
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
    public Response deleteEndOfFishing(@PathParam("id")Long id) {
        endOfFishingService.remove(id);
        return Response.ok().build();
    }

}
