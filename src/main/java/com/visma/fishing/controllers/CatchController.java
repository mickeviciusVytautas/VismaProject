package com.visma.fishing.controllers;

import com.visma.fishing.services.CatchService;
import com.visma.fishing.model.Catch;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/catch")
public class CatchController {

    @Inject
    CatchService catchService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCatch(@Valid Catch aCatch){
        return catchService.create(aCatch);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getCatch(@PathParam("id") String id) {
        return catchService.findById(id)
                .map(aCatch -> Response.status(Response.Status.FOUND).entity(aCatch).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Catch by id " + id + " was not found.").build());

    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCatches() {
        List<Catch> catchList = catchService.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        catchList.forEach(a -> jsonArrayBuilder.add(a.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCatchById(@PathParam("id")Long id) {
        catchService.remove(id);
        return Response.ok().build();
    }

}
