package strategy;

import model.Logbook;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DBSavingStrategy implements SavingStrategy {

    @PersistenceContext
    EntityManager em;

    @Override
    public void save(Logbook logbook) {
        em.persist(logbook.getDeparture());
        em.persist(logbook.getACatch());
        em.persist(logbook.getArrival());
        em.persist(logbook.getEndOfFishing());
        em.persist(logbook);
    }
}
