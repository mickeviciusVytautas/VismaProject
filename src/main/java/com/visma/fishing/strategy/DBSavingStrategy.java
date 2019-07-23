package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;

import static com.visma.fishing.messages.Messages.LOGBOOK_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.TO_DATABASE;

@Log4j2
public class DBSavingStrategy implements SavingStrategy {

    private EntityManager em;

    public DBSavingStrategy (EntityManager entityManager){
        this.em = entityManager;
    }
    @Override
    public Logbook save(Logbook logbook) {
        em.persist(logbook);
        log.info(LOGBOOK_SAVE_SUCCESS_MSG + TO_DATABASE, logbook.getId());
        return logbook;
    }

}
