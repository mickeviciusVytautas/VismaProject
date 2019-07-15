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
    public void save(Logbook logbook) {
        em.persist(logbook);
    }

}
