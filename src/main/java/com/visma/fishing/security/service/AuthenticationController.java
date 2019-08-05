package com.visma.fishing.security.service;

import com.visma.fishing.model.Role;
import com.visma.fishing.model.User;
import com.visma.fishing.security.Credentials;
import com.visma.fishing.security.key.KeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Path("/authentication")
public class AuthenticationController {

    @PersistenceContext
    private EntityManager em;
    @Context
    private UriInfo uriInfo;
    @Inject
    private KeyGenerator keyGenerator;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {

            User user = authenticate(credentials.getUsername(), credentials.getPassword());

            String token = issueToken(user);

            return Response.ok().entity(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    private String issueToken(User user) {
        Key key = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("Role", ((Role) user.getRoles().get(0)).getRoleName())
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private User authenticate(String username, String password) throws IllegalAccessException {
        try {
            return em.createQuery("select U " +
                    "from User U where U.username = ?1 and U.password = ?2", User.class)
                    .setParameter(1, username)
                    .setParameter(2, password)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new IllegalAccessException("Incorrect username or password.");
        }
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
