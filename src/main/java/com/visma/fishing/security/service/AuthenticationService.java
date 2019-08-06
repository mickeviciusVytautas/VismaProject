package com.visma.fishing.security.service;

import com.visma.fishing.model.security.Role;
import com.visma.fishing.model.security.User;
import com.visma.fishing.security.key.KeyGenerator;
import com.visma.fishing.security.utils.Credentials;
import com.visma.fishing.security.utils.PasswordEncoder;
import com.visma.fishing.security.utils.RoleName;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.visma.fishing.security.utils.PasswordEncoder.encode;

@Stateless
public class AuthenticationService {

    @PersistenceContext
    private EntityManager em;



    @Inject
    private KeyGenerator keyGenerator;

    public void createUser(User user) {
        user.setPassword(encode(user.getPassword()));
        em.persist(user);
    }

    public void createRole(Role role) {
        em.persist(role);
    }

    public Role findRoleByName(RoleName roleName) {
        return (Role) em.createNativeQuery("select R.* from ROLES R where ROLENAME = ?1", Role.class)
                .setParameter(1, roleName.toString())
                .getSingleResult();
    }

    public String authenticateUser(Credentials credentials, UriInfo uriInfo) throws IllegalAccessException {
        User user = authenticate(credentials);
        return issueToken(user, uriInfo);
    }

    private String issueToken(User user, UriInfo uriInfo) {
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

    private User authenticate(Credentials credentials) throws IllegalAccessException {
        String username = credentials.getUsername();
        String password = PasswordEncoder.encode(credentials.getPassword());
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
