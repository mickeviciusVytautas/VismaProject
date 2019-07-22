package com.visma.fishing.controllers;

import com.visma.fishing.model.Catch;
import com.visma.fishing.services.CatchService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.visma.fishing.messages.Messages.*;

@Path("/catch")
public class CatchController {


    @Inject
    private CatchService catchService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCatch(@Valid Catch aCatch){
        return Response.status(Response.Status.CREATED).entity(CATCH_SAVE_SUCCESS_MSG).entity(aCatch).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getCatch(@PathParam("id") String id) {
        return catchService.findById(id)
                .map(aCatch -> Response.status(Response.Status.FOUND).entity(aCatch).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Catch by id " + id + " is not found.").build());

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
        return catchService.updateCatchById(id, aCatch)
                .map(configuration -> Response.status(Response.Status.ACCEPTED).entity(CATCH_UPDATE_SUCCESS_MSG + id + ".").build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(CATCH_FIND_FAILED_MSG + id + ".").build());
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
