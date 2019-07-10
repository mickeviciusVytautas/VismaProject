package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.*;

public class DBSavingStrategy implements SavingStrategy {

    private EntityManager em;

    public DBSavingStrategy (EntityManager entityManager){
        this.em = entityManager;
    }
    @Override
    public Response save(Logbook logbook) {
        em.persist(logbook);
        return status(Status.CREATED).entity("Successfully saved logbook to database.").build();
    }

}
