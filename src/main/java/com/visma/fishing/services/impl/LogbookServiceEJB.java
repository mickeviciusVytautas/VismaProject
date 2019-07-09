package com.visma.fishing.services.impl;

import com.visma.fishing.services.LogbookService;
import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.model.Logbook;
import com.visma.fishing.strategy.DBSavingStrategy;
import com.visma.fishing.strategy.FileSavingStrategy;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Transactional
@Stateless
public class LogbookServiceEJB implements LogbookService {

    public static final String LOGBOOK_DISTINCT_START = "SELECT  distinct (L.*) from LOGBOOK L" +
            "    JOIN LOGBOOK_CATCH LC on L.ID = LC.LOGBOOK_ID" +
            "    JOIN CATCH C on LC.CATCHLIST_ID = C.ID";

    private static final String LOGBOOK_SELECT_START = "SELECT *  FROM LOGBOOK L";

    private static final String SELECT_LOGBOOK_BY_DEPARTURE_PORT =
                LOGBOOK_SELECT_START +
            "    LEFT JOIN DEPARTURE D ON l.DEPARTURE_ID = D.ID " +
            "    WHERE D.PORT LIKE ?1";

    private static final String SELECT_LOGBOOK_BY_ARRIVAL_PORT =
                LOGBOOK_SELECT_START +
            "    JOIN ARRIVAL A ON l.ARRIVAL_ID = A.ID " +
            "    WHERE A.PORT LIKE ?1";

    private static final String SELECT_LOGBOOK_BY_SPECIES =
                LOGBOOK_DISTINCT_START +
            "    WHERE C.SPECIES LIKE ?1";

    private static final String SELECT_LOGBOOK_WHERE_WEIGHT_IS_BIGGER =
                LOGBOOK_DISTINCT_START +
            "    WHERE C.WEIGHT > ?1";

    @Inject
    @Property(name = "databasePath",
            resource = @PropertyResource("classpath:application.properties"))
    private String databasePath;

    @Inject
    @Property(name = "inboxFolder",
            resource = @PropertyResource("classpath:application.properties"))
    private String inboxPath;

    @PersistenceContext
    private EntityManager em;

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
    public List<Logbook> findByDeparturePort(String port) {
        return em.createNativeQuery(
                SELECT_LOGBOOK_BY_DEPARTURE_PORT, Logbook.class)
                .setParameter(1, "%" + port + "%")
                .getResultList();
    }

    @Override
    public List<Logbook> findByArrivalPort(String port) {
        return em.createNativeQuery(
                SELECT_LOGBOOK_BY_ARRIVAL_PORT, Logbook.class)
                .setParameter(1, "%" + port + "%")
                .getResultList();
    }

    @Override
    public List<Logbook> findBySpecies(String species) {
        return em.createNativeQuery(
                SELECT_LOGBOOK_BY_SPECIES, Logbook.class)
                .setParameter(1, "%" + species + "%")
                .getResultList();
    }

    @Override
    public List<Logbook> findWhereCatchWeightIsBigger(Long weight) {
        return em.createNativeQuery(
                SELECT_LOGBOOK_WHERE_WEIGHT_IS_BIGGER, Logbook.class)
                .setParameter(1, weight)
                .getResultList();
    }

    @Override
    public Response create(Logbook logbook) {
        if (logbook.getConnectionType() == ConnectionType.NETWORK) {
            return new DBSavingStrategy(em).save(logbook);
        }
        return new FileSavingStrategy(databasePath).save(logbook);
    }

    @Override
    public Response createBySatellite(Logbook logbook){
        return new FileSavingStrategy(inboxPath).save(logbook);
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
