package com.visma.fishing.security.filter;

import com.visma.fishing.security.utils.RoleName;
import com.visma.fishing.security.key.KeyGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.security.Key;

@JWTTokenNeeded
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    private Logger logger = LogManager.getLogger(JWTTokenNeededFilter.class);
    @Inject
    private KeyGenerator keyGenerator;
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.error("invalid authorizationHeader : " + authorizationHeader);
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Authorization header must be provided.")
                            .build());
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        Key key = keyGenerator.generateKey();

        validateToken(key, token, requestContext);
        validateRole(key, token, requestContext);

    }

    private void validateRole(Key key, String token, ContainerRequestContext requestContext) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Method method = resourceInfo.getResourceMethod();
        if (method != null) {
            JWTTokenNeeded jwtContext = method.getAnnotation(JWTTokenNeeded.class);
            RoleName permission = jwtContext.Permission();
            if (permission != RoleName.NO_RIGHTS) {
                // Get Role from jwt
                String roles = claims.get("Role", String.class);
                RoleName roleUser = RoleName.valueOf(roles);

                // if role allowed != role jwt -> UNAUTHORIZED
                if (permission.ordinal() > roleUser.ordinal()) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid role.").build());
                }

            }
        }
    }

    private void validateToken(Key key, String token, ContainerRequestContext requestContext) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("Valid token: " + token);
        } catch (Exception e) {
            logger.error("Invalid token: " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token.").build());
        }
    }
}
