package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;
import com.visma.fishing.services.impl.LogbookServiceEJB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;

import static com.visma.fishing.messages.Messages.LOGBOOK_SAVE_SUCCESS_MSG;

public class DBSavingStrategy implements SavingStrategy {
    private final Logger log = LogManager.getLogger(LogbookServiceEJB.class);

    private EntityManager em;

    public DBSavingStrategy(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Logbook save(Logbook logbook) {
        em.persist(logbook);
        log.info(LOGBOOK_SAVE_SUCCESS_MSG, logbook.getId());
        return logbook;
    }

}
