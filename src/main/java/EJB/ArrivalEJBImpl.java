package EJB;

import model.Arrival;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ArrivalEJBImpl implements ArrivalEJB {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Arrival> findAll() {
        Query q = em.createQuery("SELECT a FROM Arrival a");
        return q.getResultList();
    }

    @Override
    public void create(Arrival arrival) {
        em.persist(arrival);
    }

    @Override
    public void update(Long id, Arrival arrival) {
        Arrival entity = em.find(Arrival.class, id);
        entity.setPort(arrival.getPort());
        em.merge(entity);

    }

    @Override
    public void remove(Long id) {
        em.remove(id);
    }


}
