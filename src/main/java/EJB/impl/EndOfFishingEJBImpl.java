package EJB.impl;

import EJB.EndOfFishingEJB;
import model.EndOfFishing;
import model.Logbook;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class EndOfFishingEJBImpl implements EndOfFishingEJB {

    @PersistenceContext
    EntityManager em;

    @Override
    public List findAll() {
        Query q = em.createQuery("SELECT a FROM EndOfFishing a");
        return q.getResultList();
    }

    @Override
    public EndOfFishing findById(Long id) {
        return em.find(EndOfFishing.class, id);
    }

    @Override
    public void create(EndOfFishing endOfFishing) {
        em.persist(endOfFishing);
    }

    @Override
    public void update(Long id, EndOfFishing endOfFishing) {
        EndOfFishing entity = em.find(EndOfFishing.class, id);
        entity.setDate(endOfFishing.getDate());
    }

    @Override
    public void remove(Long id) {
        EndOfFishing entity = em.find(EndOfFishing.class, id);
        em.remove(entity);
    }
}
