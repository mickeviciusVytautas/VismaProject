package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;

import javax.persistence.EntityManager;

public class DBSavingStrategy implements SavingStrategy {

    private EntityManager em;

    public DBSavingStrategy (EntityManager entityManager){
        this.em = entityManager;
    }
    @Override
    public Logbook save(Logbook logbook) {
        em.persist(logbook);
        return logbook;
    }

}
