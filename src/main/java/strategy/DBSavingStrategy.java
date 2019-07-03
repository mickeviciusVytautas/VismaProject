package strategy;

import model.Logbook;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.core.Response;

public class DBSavingStrategy implements SavingStrategy {

    EntityManager em;

    public DBSavingStrategy (EntityManager entityManager){
        this.em = entityManager;
    }
    @Override
    public Response save(Logbook logbook) {
        em.persist(logbook.getDeparture());
        logbook.getCatchList().forEach(c -> em.persist(c));
        em.persist(logbook.getArrival());
        em.persist(logbook.getEndOfFishing());
        em.persist(logbook);
        return Response.ok("Successfully saved logbook to database.").build();
    }
}
