package com.visma.fishing.services.impl;

import com.visma.fishing.model.Archive;
import com.visma.fishing.model.Logbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.TypedQuery;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveServiceEJBTest extends LogbookTestInfrastructure {

    @InjectMocks
    private ArchiveServiceEJB service;


    @SuppressWarnings("unchecked")
    private TypedQuery<Archive> archiveQuery = (TypedQuery<Archive>) mock(TypedQuery.class);

    @Test
    public void archiveLogbooks() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(logbookQuery);
        when(logbookQuery.setParameter(anyString(), any())).thenReturn(logbookQuery);
        when(logbookQuery.getResultList()).thenReturn(logbooks);

        service.archiveLogbooks();
        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(em, times(1)).persist(any(Archive.class));
        verify(em, times(1)).remove(any(Logbook.class));
    }

    @Test
    public void removeArchives() {
        when(em.createNativeQuery(anyString(), eq(Archive.class))).thenReturn(archiveQuery);
        when(archiveQuery.setParameter(anyString(), any())).thenReturn(archiveQuery);
        when(archiveQuery.getResultList()).thenReturn(new ArrayList<Archive>() {{
            add(new Archive());
        }});

        service.removeArchives();

        verify(em, times(1)).remove(any(Archive.class));
    }
}