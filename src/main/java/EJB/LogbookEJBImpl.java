package EJB;

import model.Logbook;
import strategy.SavingContext;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Stateless
public class LogbookEJBImpl implements LogbookEJB {

    @PersistenceContext
    EntityManager em;

    private SavingContext savingContext;
    @Override
    public List findAll() {
        Query q = em.createQuery("SELECT a FROM Logbook a");
        return q.getResultList();
    }

    @Override
    public Optional<Logbook> findById(Long id) {
        return Optional.ofNullable(em.find(Logbook.class, id));
    }

    @Override
    public void create(Logbook logbook, String location) {
        em.persist(logbook.getDeparture());
        em.persist(logbook.getACatch());
        em.persist(logbook.getArrival());
        em.persist(logbook.getEndOfFishing());
        em.persist(logbook);

    }

    @Override
    public void update(Long id, Logbook logbook) {
        Logbook entity = em.find(Logbook.class, id);
        entity.setACatch(logbook.getACatch());
        entity.setArrival(logbook.getArrival());
        entity.setDeparture(logbook.getDeparture());
        entity.setEndOfFishing(logbook.getEndOfFishing());
        em.merge(entity);
    }

    @Override
    public void remove(Long id) {
        Logbook entity = em.find(Logbook.class, id);
        em.remove(entity);
    }

}
