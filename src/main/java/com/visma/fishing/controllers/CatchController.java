package com.visma.fishing.controllers;

import com.visma.fishing.exception.ConcurrentChangesException;
import com.visma.fishing.exception.EntityNotFoundException;
import com.visma.fishing.model.Catch;
import com.visma.fishing.services.CatchService;

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

import static com.visma.fishing.messages.Messages.CATCH_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.CATCH_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.CATCH_UPDATE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.format;

@Path("/catch")
public class CatchController {

    @Inject
    private CatchService catchService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCatch(@Valid Catch aCatch) {
        return Response.status(Response.Status.CREATED).entity(CATCH_SAVE_SUCCESS_MSG).entity(aCatch).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getCatch(@PathParam("id") Long id) {
        return catchService.findById(id)
                .map(aCatch -> Response.status(Response.Status.FOUND).entity(aCatch).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(format(CATCH_FIND_FAILED_MSG, id)).build());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catch> getCatches() {
        return catchService.findAll();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCatchById(Catch aCatch) {
        try {
            catchService.updateCatch(aCatch);
            return Response.status(Response.Status.ACCEPTED).entity(format(CATCH_UPDATE_SUCCESS_MSG, aCatch.getId())).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ConcurrentChangesException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteCatchById(@PathParam("id") Long id) {
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
