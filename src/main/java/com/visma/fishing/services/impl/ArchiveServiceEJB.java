package com.visma.fishing.services.impl;

import com.visma.fishing.model.Archive;
import com.visma.fishing.model.Logbook;
import com.visma.fishing.services.ArchiveService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless

public class ArchiveServiceEJB implements ArchiveService {

    private Logger log = LogManager.getLogger(ArchiveServiceEJB.class);

    @PersistenceContext
    private EntityManager em;

    @Schedule(hour = "*", minute = "*/10", second = "*", persistent = false)
    @SuppressWarnings("unchecked")
    public void archiveLogbooks() {
        Date date = new Date();
        List<Logbook> logbooks = em.createNativeQuery(
                "SELECT L.* FROM LOGBOOK L " +
                        "where L.CREATION_DATE " +
                        "<= DATEADD(year, -1, CURDATE())", Logbook.class)
                .setParameter(1, DateUtils.addYears(date, -1))
                .getResultList();
        logbooks.forEach(logbook -> {
            em.persist(new Archive(logbook.toString()));
            em.remove(logbook);
        });
    }

    @Schedule(hour = "*", minute = "*/10", second = "*", persistent = false)
    @SuppressWarnings("unchecked")
    public void removeArchives() {
        Date date = new Date();
        List<Archive> archives = em.createNativeQuery(
                "SELECT A.* FROM ARCHIVE A " +
                        "WHERE A.ARCHIVINGDATE <= DATEADD(year, -1, CURDATE())", Archive.class)
                .getResultList();
        archives.forEach(archive -> em.remove(archive));
    }
}
