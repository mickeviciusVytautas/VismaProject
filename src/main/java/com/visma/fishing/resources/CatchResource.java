package com.visma.fishing.resources;

import com.visma.fishing.EJB.CatchEjb;
import com.visma.fishing.model.Catch;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/catch")
public class CatchResource {

    @Inject
    CatchEjb catchEjb;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCatch(Catch aCatch){
        catchEjb.create(aCatch);
        return Response.ok(aCatch.toJson()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getCatch(@PathParam("id") Long id) {
        Catch aCatch = catchEjb.findById(id);
        return Response.ok(aCatch)
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCatches() {
        List<Catch> catchList = catchEjb.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        catchList.forEach(a -> jsonArrayBuilder.add(a.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCatchById(@PathParam("id")Long id) {
        catchEjb.remove(id);
        return Response.ok().build();
    }

}
