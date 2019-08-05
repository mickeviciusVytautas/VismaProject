package com.visma.fishing.security.service;

import com.visma.fishing.model.Role;
import com.visma.fishing.model.User;
import com.visma.fishing.security.RoleName;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AuthenticationService {

    @PersistenceContext
    private EntityManager em;

    public void createUser(User user) {
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
}
