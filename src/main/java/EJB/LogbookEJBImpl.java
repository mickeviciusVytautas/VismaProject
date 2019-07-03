package EJB;

import model.Logbook;
import strategy.SavingStrategy;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Stateless
public class LogbookEJBImpl implements LogbookEJB {

    @PersistenceContext
    EntityManager em;

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
    public Response create(Logbook logbook, SavingStrategy savingStrategy) {
       savingStrategy.save(logbook);
        return Response.ok().build();
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
