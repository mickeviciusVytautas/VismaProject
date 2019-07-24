package com.visma.fishing.services.impl;

import com.visma.fishing.exception.ConcurrentChangesException;
import com.visma.fishing.exception.EntityNotFoundException;
import com.visma.fishing.model.CommunicationType;
import com.visma.fishing.model.Logbook;
import com.visma.fishing.services.LogbookService;
import com.visma.fishing.strategy.DBSavingStrategy;
import com.visma.fishing.strategy.FileSavingStrategy;
import com.visma.fishing.strategy.SavingStrategy;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.messages.Messages.LOGBOOK_CONCURRENT_CHANGES_MSG;
import static com.visma.fishing.messages.Messages.LOGBOOK_FIND_FAILED_MSG;
import static com.visma.fishing.messages.Messages.LOGBOOK_REMOVED_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.LOGBOOK_UPDATE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.format;
import static com.visma.fishing.queries.Queries.LOGBOOK_FIND_BY_ARRIVAL_PORT;
import static com.visma.fishing.queries.Queries.LOGBOOK_FIND_BY_DEPARTURE_DATE;
import static com.visma.fishing.queries.Queries.LOGBOOK_FIND_BY_DEPARTURE_PORT;
import static com.visma.fishing.queries.Queries.LOGBOOK_FIND_BY_SPECIES;
import static com.visma.fishing.queries.Queries.LOGBOOK_FIND_WHERE_WEIGHT_IS_BIGGER;
import static com.visma.fishing.queries.Queries.LOGBOOK_FIND_WHERE_WEIGHT_IS_LOWER;

@Stateless
public class LogbookServiceEJB implements LogbookService {

    private Logger log = LogManager.getLogger(LogbookServiceEJB.class);

    @Inject
    @Property(name = "databasePath",
            resource = @PropertyResource("classpath:application.properties"),
            defaultValue = "C:\\dev\\database\\")
    private String databasePath;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Logbook> findAll() {
        TypedQuery<Logbook> q = em.createNamedQuery("logbook.findAll", Logbook.class);
        return q.getResultList();
    }

    @Override
    public Optional<Logbook> findById(String id) {
        return Optional.ofNullable(em.find(Logbook.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Logbook> findByDeparturePort(String port) {
        return em.createNativeQuery(
                LOGBOOK_FIND_BY_DEPARTURE_PORT, Logbook.class)
                .setParameter(1,  "%" + port)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Logbook> findByArrivalPort(String port) {
        return em.createNativeQuery(
                LOGBOOK_FIND_BY_ARRIVAL_PORT, Logbook.class)
                .setParameter(1, "%" + port)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Logbook> findBySpecies(String species) {
        return em.createNativeQuery(
                LOGBOOK_FIND_BY_SPECIES, Logbook.class)
                .setParameter(1, "%" + species)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Logbook> findByWeight(Long weight, boolean searchWithLowerWeight) {
        if(searchWithLowerWeight) {
            return em.createNativeQuery(
                    LOGBOOK_FIND_WHERE_WEIGHT_IS_LOWER, Logbook.class)
                    .setParameter(1, weight)
                    .getResultList();
        }
        return em.createNativeQuery(
                LOGBOOK_FIND_WHERE_WEIGHT_IS_BIGGER, Logbook.class)
                .setParameter(1, weight)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Logbook> findByDeparturePeriod(String start, String end){
        return em.createNativeQuery(
                LOGBOOK_FIND_BY_DEPARTURE_DATE, Logbook.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }

    @Override
    public void create(Logbook logbook) {
        SavingStrategy savingStrategy;
        if (logbook.getCommunicationType() == CommunicationType.NETWORK) {
            savingStrategy = new DBSavingStrategy(em);
        } else {
            savingStrategy = new FileSavingStrategy(databasePath);
        }
        savingStrategy.save(logbook);
    }

    @Override
    public void updateLogbook(Logbook logbook)   {
        if (em.find(Logbook.class, logbook.getId()) == null) {
            log.info(LOGBOOK_FIND_FAILED_MSG, logbook.getId());
            throw new EntityNotFoundException(format(LOGBOOK_FIND_FAILED_MSG, logbook.getId()));
        }   try {
            em.merge(logbook);
            em.flush();
        } catch (OptimisticLockException e){
            log.error(LOGBOOK_CONCURRENT_CHANGES_MSG, logbook.getId());
            throw new ConcurrentChangesException(format(LOGBOOK_CONCURRENT_CHANGES_MSG, logbook.getId()));
        }
        log.info(LOGBOOK_UPDATE_SUCCESS_MSG, logbook.getId());


    }

    @Override
    public void remove(String id) {
        Optional.ofNullable(em.find(Logbook.class, id))
                .ifPresent(entity -> {
                    em.remove(entity);
                    log.info(LOGBOOK_REMOVED_SUCCESS_MSG, id);
                });
    }

    @Override
    public List<Logbook> saveAll(List<Logbook> logbooks) {
        for (Logbook logbook :
                logbooks) {
            em.persist(logbook);
        }
        return logbooks;
    }
}
