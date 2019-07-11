package com.visma.fishing.controllers;

import com.visma.fishing.model.Catch;
import com.visma.fishing.services.CatchService;

import javax.inject.Inject;
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
        return catchService.findById(id);

    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catch> getCatches() {
        return catchService.findAll();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCatchById(@PathParam("id") String id, Catch aCatch){
        return catchService.update(id, aCatch);
    }

    @DELETE
    @Path("{id}")
    public Response deleteCatchById(@PathParam("id")String id) {
        catchService.remove(id);
        return Response.ok().build();
    }

    @GET
    @Path("/search/{species}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catch> getCatchesBySpecies(@PathParam("species") String species) {
        return catchService.findBySpecies(species);
    }


    @GET
    @Path("/search/weight/{weight}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catch> getCatchesByWeight(@PathParam("weight") Long weight, @QueryParam("lower") boolean searchWithLowerWeight) {
        return catchService.findByWeight(weight, searchWithLowerWeight);
    }
}
