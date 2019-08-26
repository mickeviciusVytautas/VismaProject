package com.visma.fishing.services.impl;

import com.visma.fishing.model.Archive;
import com.visma.fishing.model.Logbook;
import com.visma.fishing.services.ArchiveService;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ArchiveServiceEJB implements ArchiveService {

    private Logger log = LogManager.getLogger(ArchiveServiceEJB.class);

    @PersistenceContext
    private EntityManager em;

    @Inject
    @Property(name = "archivingInterval",
            resource = @PropertyResource("classpath:application.properties"),
            defaultValue = "year")
    private String archivingInterval;

    @Inject
    @Property(name = "archiveDeletionInterval",
            resource = @PropertyResource("classpath:application.properties"),
            defaultValue = "year")
    private String archiveDeletionInterval;

    /**
     * Archives logbooks that are created in a specified interval of time
     */
    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    @SuppressWarnings("unchecked")
    public void archiveLogbooks() {
        List<Logbook> logbooks = em.createNativeQuery(
                "SELECT L.* FROM LOGBOOK L " +
                        "where L.CREATION_DATE " +
                        "<= DATEADD(:interval, -1, CURRENT_TIMESTAMP())", Logbook.class)
                .setParameter("interval", archivingInterval)
                .getResultList();
        logbooks.forEach(logbook -> {
            em.persist(new Archive(logbook.toString()));
            em.remove(logbook);
            log.info("Archived logbook with id {}.", logbook.getId());
        });
    }

    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    @SuppressWarnings("unchecked")
    public void removeArchives() {
        List<Archive> archives = em.createNativeQuery(
                "SELECT A.* FROM ARCHIVE A " +
                        "WHERE A.ARCHIVINGDATE <= DATEADD(:interval, -2, CURRENT_TIMESTAMP())", Archive.class)
                .setParameter("interval", archiveDeletionInterval)
                .getResultList();
        archives.forEach(archive -> {
            em.remove(archive);
            log.info("Deleted archive with id {}.", archive.getId());
        });
    }
}
