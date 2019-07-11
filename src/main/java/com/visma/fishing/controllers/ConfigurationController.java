package com.visma.fishing.controllers;

import com.visma.fishing.model.configuration.Configuration;
import com.visma.fishing.services.ConfigurationService;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/config")
public class ConfigurationController {

    @Inject
    ConfigurationService configurationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createConfiguration(@Valid Configuration configuration){
        return configurationService.create(configuration);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getConfiguration(@PathParam("id") String id) {
        return configurationService.findById(id);

    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Configuration> getConfigurations() {
        return configurationService.findAll();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateConfigurationById(@PathParam("id") String id, Configuration configuration){
        return configurationService.update(id, configuration);
    }

    @DELETE
    @Path("{id}")
    public Response deleteConfigurationById(@PathParam("id")String id) {
        configurationService.remove(id);
        return Response.ok().build();
    }

    @GET
    @Path("/value/{key}/{defaultValue}")
    public String findValueByKey(@PathParam("key") String key, @PathParam("defaultValue") String defaultValue){
        return configurationService.findValueByKey(key, defaultValue);
    }
}
