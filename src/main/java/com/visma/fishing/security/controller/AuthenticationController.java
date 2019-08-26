package com.visma.fishing.security.controller;

import com.visma.fishing.model.security.User;
import com.visma.fishing.security.service.AuthenticationService;
import com.visma.fishing.security.utils.UserCredentials;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/authentication")
public class AuthenticationController {

    @Context
    private UriInfo uriInfo;

    @Inject
    private AuthenticationService authenticationService;
    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        authenticationService.createUser(user);
        return Response.ok().build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(UserCredentials credentials) {
        try {
            String token = authenticationService.authenticateUser(credentials, uriInfo);
            return Response.ok().entity(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }
}
