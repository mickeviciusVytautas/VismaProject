package EJB;

import model.Catch;
import model.Logbook;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CatchEjbImpl implements CatchEjb {

    @PersistenceContext(name = "prod")
    EntityManager em;

    @Override
    public List<Catch> findAll() {
        return this.em.createNamedQuery(Catch.FIND_ALL).getResultList();

    }

    @Override
    public void create(Catch aCatch) {
        em.persist(aCatch);
    }

    @Override
    public void update(Long id, Catch aCatch) {
        Catch entity = em.find(Catch.class, id);
        entity.setSpecies(aCatch.getSpecies());
        entity.setWeight(aCatch.getWeight());
        em.merge(entity);
    }

    @Override
    public void remove(Long id) {
        em.remove(id);
    }
}
