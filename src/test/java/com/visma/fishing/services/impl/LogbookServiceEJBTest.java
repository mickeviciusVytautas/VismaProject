package com.visma.fishing.services.impl;

import com.visma.fishing.exception.ConcurrentChangesException;
import com.visma.fishing.model.Logbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogbookServiceEJBTest extends LogbookTestInfrastructure {

    @InjectMocks
    public LogbookServiceEJB service;

    @Test
    public void findAllShouldReturnLogbookList() {
        when(em.createNamedQuery("logbook.findAll", Logbook.class)).thenReturn(logbookQuery);
        when(logbookQuery.getResultList()).thenReturn(logbooks);

        results = service.findAll();

        assertEquals("Logbook list size should be 1.", 1, results.size());
        verify(em, times(1)).createNamedQuery("logbook.findAll", Logbook.class);
    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Logbook.class), anyString())).thenReturn(logbookOne);

        Optional<Logbook> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(Logbook.class), anyString());
        assertTrue("Should contain logbook.", optional.isPresent());
        assertEquals("Should contain logbook.", logbookOne, optional.get());
    }

    @Test
    public void createShouldInvokeEMPersist() {
        service.create(logbookOne);
        verify(em, times(1)).persist(any(Logbook.class));

    }

    @Test
    public void removeShouldInvokeEntityManagersRemove() {
        when(em.find(eq(Logbook.class), anyString())).thenReturn(logbookOne);
        service.remove(ID_1);

        verify(em, times(1)).remove(logbookOne);
    }

    @Test
    public void updateShouldInvokeEMFind() throws ConcurrentChangesException {
        when(em.find(eq(Logbook.class), any())).thenReturn(logbookOne);
        service.updateLogbook(logbookOne);
        verify(em, times(1)).find(eq(Logbook.class), any());
    }

    @Test
    public void findByDeparturePortShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(logbookQuery);
        when(logbookQuery.setParameter(anyInt(), anyString())).thenReturn(logbookQuery);
        when(logbookQuery.getResultList()).thenReturn(logbooks);

        results = service.findByArrivalPort(PORT_DEPARTURE_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(logbookQuery, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Logbook list size should be 1.", 1, results.size());
    }

    @Test
    public void findByArrivalPortShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(logbookQuery);
        when(logbookQuery.setParameter(anyInt(), anyString())).thenReturn(logbookQuery);
        when(logbookQuery.getResultList()).thenReturn(logbooks);

        results = service.findByArrivalPort(PORT_ARRIVAL_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(logbookQuery, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Logbook list size should be 1.", 1, results.size());
    }

    @Test
    public void findBySpeciesShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(logbookQuery);
        when(logbookQuery.setParameter(anyInt(), anyString())).thenReturn(logbookQuery);
        when(logbookQuery.getResultList()).thenReturn(logbooks);

        results = service.findBySpecies(SPECIES_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(logbookQuery, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Logbook list size should be 1.", 1, results.size());
    }

    @Test
    public void findWhereCatchWeightIsBiggerShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(logbookQuery);
        when(logbookQuery.setParameter(anyInt(), anyLong())).thenReturn(logbookQuery);
        when(logbookQuery.getResultList()).thenReturn(logbooks);

        results = service.findByWeight(WEIGHT, false);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(logbookQuery, times(1)).setParameter(anyInt(), anyLong());
        assertEquals("Logbook list size should be 1.", 1, results.size());
    }

    @Test
    public void findByDeparturePeriodShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(logbookQuery);
        when(logbookQuery.setParameter(anyInt(), anyString())).thenReturn(logbookQuery);
        when(logbookQuery.getResultList()).thenReturn(logbooks);

        results = service.findByDeparturePeriod(DATE_1.toString(), DATE_2.toString());

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(logbookQuery, times(2)).setParameter(anyInt(), anyString());
        assertEquals("Logbook list size should be 1.", 1, results.size());
    }

}