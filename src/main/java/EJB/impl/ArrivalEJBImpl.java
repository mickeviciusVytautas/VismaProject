package EJB.impl;

import EJB.ArrivalEJB;
import model.Arrival;
import model.Logbook;

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
    public Arrival findById(Long id) {
        return em.find(Arrival.class, id);
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
        Arrival entity = em.find(Arrival.class, id);
        em.remove(entity);
    }


}
