package EJB;

import model.Logbook;
import sun.rmi.runtime.Log;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LogbookEJBImpl implements LogbookEJB {

    @PersistenceContext(name = "prod")
    EntityManager em;

    @Override
    public List findAll() {
        return this.em.createNamedQuery(Logbook.FIND_ALL).getResultList();
    }

    @Override
    public void create(Logbook logbook) {
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
        em.merge(id);
    }

}
