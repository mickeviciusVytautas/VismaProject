package com.visma.fishing.controllers;

import com.visma.fishing.model.configuration.Configuration;
import com.visma.fishing.services.ConfigurationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.visma.fishing.messages.Messages.CONFIGURATION_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.CONFIGURATION_SAVE_SUCCESS_MSG;

@Path("/config")
public class ConfigurationController {

    private static final String CONFIGURATION_UPDATE_SUCCESS_MSG = "Successfully updated departure with id ";
    private static final String CONFIGURATION_UPDATE_FAILED_MSG = "Failed to update departure with id ";

    @Inject
    private ConfigurationService configurationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createConfiguration(@Valid Configuration configuration){
        configurationService.create(configuration);
        return Response.status(Response.Status.CREATED).entity(CONFIGURATION_SAVE_SUCCESS_MSG).entity(configuration).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getConfiguration(@PathParam("id") String id) {
        return configurationService.findById(id).map(
                configuration -> Response.status(Response.Status.FOUND).entity(configuration).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(CONFIGURATION_FIND_FAILED_MSG + id + ".").build());

    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Configuration> getConfigurations() {
        return configurationService.findAll();
    }

    @PUT
    @Path("{key}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateConfigurationByKey(@PathParam("key") String key, Configuration update) {
        return configurationService.updateConfigurationByKey(key, update)
                .map(configuration -> Response.status(Response.Status.ACCEPTED).entity(CONFIGURATION_UPDATE_SUCCESS_MSG + update.getKey() + ".").build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(CONFIGURATION_UPDATE_FAILED_MSG + key + ".").build());
    }

    @DELETE
    @Path("{id}")
    public void deleteConfigurationById(@PathParam("id") String id) {
        configurationService.remove(id);
    }

    @GET
    @Path("/value/{key}/{defaultValue}")
    public String findValueByKey(@PathParam("key") String key, @PathParam("defaultValue") String defaultValue){
        return configurationService.findValueByKey(key, defaultValue);
    }
}
