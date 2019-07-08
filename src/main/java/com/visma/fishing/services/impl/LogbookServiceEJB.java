package com.visma.fishing.services.impl;

import com.visma.fishing.services.LogbookService;
import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.model.Logbook;
import com.visma.fishing.strategy.DBSavingStrategy;
import com.visma.fishing.strategy.FileSavingStrategy;
import com.visma.fishing.strategy.SavingStrategy;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Stateless
public class LogbookServiceEJB implements LogbookService {

    @Inject
    @Property(name = "databasePath",
            resource = @PropertyResource("classpath:application.properties"))
    private String DATABASE_PATH;

    @Inject
    @Property(name = "inboxFolder",
            resource = @PropertyResource("classpath:application.properties"))
    private String INBOX_PATH;

    @PersistenceContext
    private EntityManager em;

    private SavingStrategy savingStrategy;

    @Override
    public List<Logbook> findAll() {
        TypedQuery<Logbook> q = em.createQuery("SELECT a FROM Logbook a", Logbook.class);
        return q.getResultList();
    }

    @Override
    public Optional<Logbook> findById(Long id) {
        return Optional.ofNullable(em.find(Logbook.class, id));
    }

    @Override
    public void findByDeparturePort(String portName) {
        Query q = em.createQuery("SELECT l FROM Logbook l ");

    }
    @Override
    public Response create(Logbook logbook) {
        if (logbook.getConnectionType() == ConnectionType.NETWORK) {
            return new DBSavingStrategy(em).save(logbook);
        }
        return new FileSavingStrategy(DATABASE_PATH).save(logbook);
    }

    @Override
    public Response createBySatellite(Logbook logbook){
        return new FileSavingStrategy(INBOX_PATH).save(logbook);
    }



    @Override
    public void update(Long id, Logbook logbook) {
        Logbook entity = em.find(Logbook.class, id);
        entity.setCatchList(logbook.getCatchList());
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
