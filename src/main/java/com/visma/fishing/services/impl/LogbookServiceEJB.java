package com.visma.fishing.services.impl;

import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.model.Logbook;
import com.visma.fishing.services.LogbookService;
import com.visma.fishing.strategy.DBSavingStrategy;
import com.visma.fishing.strategy.FileSavingStrategy;
import com.visma.fishing.strategy.SavingStrategy;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Transactional
@Stateless
public class LogbookServiceEJB implements LogbookService {

    private static final String QUERY_FIND_DISTINCT =
            " SELECT  distinct (L.*) from LOGBOOK L" +
            " JOIN LOGBOOK_CATCH LC on L.ID = LC.LOGBOOK_ID" +
            " JOIN CATCH C on LC.CATCHLIST_ID = C.ID";

    private static final String QUERY_FIND = "SELECT *  FROM LOGBOOK L";

    private static final String QUERY_FIND_BY_DEPARTURE_PORT =
                QUERY_FIND +
            " LEFT JOIN DEPARTURE D ON l.DEPARTURE_ID = D.ID " +
            " WHERE D.PORT LIKE ?1";

    private static final String QUERY_FIND_BY_ARRIVAL_PORT =
                QUERY_FIND +
            " JOIN ARRIVAL A ON l.ARRIVAL_ID = A.ID " +
            " WHERE A.PORT LIKE ?1";

    private static final String QUERY_FIND_BY_SPECIES =
                QUERY_FIND_DISTINCT +
            " WHERE C.SPECIES LIKE ?1";

    private static final String QUERY_FIND_WHERE_WEIGHT_IS_BIGGER =
            QUERY_FIND_DISTINCT +
                    " WHERE C.WEIGHT >= ?1";

    private static final String QUERY_FIND_WHERE_WEIGHT_IS_LOWER =
            QUERY_FIND_DISTINCT +
                    " WHERE C.WEIGHT < ?1";

    private static final String QUERY_FIND_BY_DEPARTURE_DATE =
                QUERY_FIND +
                " join DEPARTURE D on L.DEPARTURE_ID = D.ID" +
                " where D.DATE between ?1 and ?2";

    @Inject
    @Property(name = "databasePath",
            resource = @PropertyResource("classpath:application.properties"),
            defaultValue = "C:\\dev\\database\\")
    private String databasePath;

    @Inject
    @Property(name = "inboxFolder",
            resource = @PropertyResource("classpath:application.properties"))
    private String inboxPath;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Logbook> findAll() {
        TypedQuery<Logbook> q = em.createNamedQuery("logbook.findAll", Logbook.class);
        return q.getResultList();
    }

    @Override
    public Response findById(String id) {
        return Optional.ofNullable(em.find(Logbook.class, id))
                .map(logbook -> Response.status(Response.Status.FOUND).entity(logbook).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Logbook by id " + id + " was not found.").build());
    }

    @Override
    public List<Logbook> findByDeparturePort(String port) {
        return em.createNativeQuery(
                QUERY_FIND_BY_DEPARTURE_PORT, Logbook.class)
                .setParameter(1,  "%" + port)
                .getResultList();
    }

    @Override
    public List<Logbook> findByArrivalPort(String port) {
        return em.createNativeQuery(
                QUERY_FIND_BY_ARRIVAL_PORT, Logbook.class)
                .setParameter(1, "%" + port)
                .getResultList();
    }

    @Override
    public List<Logbook> findBySpecies(String species) {
        return em.createNativeQuery(
                QUERY_FIND_BY_SPECIES, Logbook.class)
                .setParameter(1, "%" + species)
                .getResultList();
    }

    @Override
    public List<Logbook> findByWeight(Long weight, boolean searchWithLowerWeight) {
        if(searchWithLowerWeight) {
            return em.createNativeQuery(
                    QUERY_FIND_WHERE_WEIGHT_IS_LOWER, Logbook.class)
                    .setParameter(1, weight)
                    .getResultList();
        }
        return em.createNativeQuery(
                QUERY_FIND_WHERE_WEIGHT_IS_BIGGER, Logbook.class)
                .setParameter(1, weight)
                .getResultList();
    }

    @Override
    public List<Logbook> findByDeparturePeriod(String start, String end){
        return em.createNativeQuery(
                QUERY_FIND_BY_DEPARTURE_DATE, Logbook.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }

    @Override
    public Response create(Logbook logbook) {
        SavingStrategy savingStrategy;
        if (logbook.getConnectionType() == ConnectionType.NETWORK) {
            savingStrategy = new DBSavingStrategy(em);
        } else {
            savingStrategy = new FileSavingStrategy(databasePath);
        }
        return savingStrategy.save(logbook);
    }

    @Override
    public Response update(String id, Logbook logbook) {
        Logbook entity = em.find(Logbook.class, id);
        entity.setCatchList(logbook.getCatchList());
        entity.setArrival(logbook.getArrival());
        entity.setDeparture(logbook.getDeparture());
        entity.setEndOfFishing(logbook.getEndOfFishing());
        em.merge(entity);
        return Response.status(Response.Status.ACCEPTED).entity("Successfully updated logbook.").build();
    }

    @Override
    public void remove(String id) {
        Logbook entity = em.find(Logbook.class, id);
        em.remove(entity);
    }

}
