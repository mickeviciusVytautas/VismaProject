package strategy;

import model.Logbook;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public class DBSavingStrategy implements SavingStrategy {

    @PersistenceUnit(unitName = "test")
    EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Logbook logbook) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.persist(logbook.getDeparture());
        em.persist(logbook.getACatch());
        em.persist(logbook.getArrival());
        em.persist(logbook.getEndOfFishing());
        em.persist(logbook);
    }
}
