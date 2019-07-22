package com.visma.fishing.services.impl;

import com.visma.fishing.exception.TransactionFailedException;
import com.visma.fishing.model.Arrival;
import com.visma.fishing.model.Catch;
import com.visma.fishing.model.CommunicationType;
import com.visma.fishing.model.Departure;
import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.model.Logbook;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogbookServiceEJBTest {

    private static final String PORT_ARRIVAL_1= "port arrival 1";
    private static final String PORT_DEPARTURE_1 = "port departure 1";
    private static final String SPECIES_1 = "species 1";
    private static final String ID_1 = "ID 1";
    private static final Long WEIGHT = 10L;
    private static final Date DATE_1 = new Date();
    private static final Date DATE_2 = new Date();
    @Mock
    private EntityManager em;

    @InjectMocks
    private LogbookServiceEJB service;

    private Logbook logbookOne;


    private List<Logbook> logbookList = new ArrayList<>();

    private List<Logbook> resultList = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private
    TypedQuery<Logbook> query = (TypedQuery<Logbook>) mock(TypedQuery.class);

    @Before
    public void init() {
        Logbook.LogbookBuilder logbookBuilder = new Logbook.LogbookBuilder();
        Departure departure = new Departure(PORT_DEPARTURE_1, DATE_1);
        EndOfFishing endOfFishing = new EndOfFishing(DATE_2);
        Arrival arrival = new Arrival(PORT_ARRIVAL_1, DATE_1);
        Catch aCatch = new Catch(SPECIES_1, WEIGHT);
        List<Catch> catchList = new ArrayList<Catch>(){{add(aCatch);}};
        logbookOne = logbookBuilder
                .withArrival(arrival)
                .withDeparture(departure)
                .withEndOfFishing(endOfFishing)
                .withCatchList(catchList)
                .withCommunicationType(CommunicationType.NETWORK)
                .withId(ID_1)
                .build();
        logbookOne.setVersion(0L);
        logbookList.add(logbookOne);

    }

    @Test
    public void findAllShouldReturnLogbookList() {
        when(em.createNamedQuery("logbook.findAll", Logbook.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(logbookList);

        resultList = service.findAll();

        assertEquals("Logbook list size should be 1.", 1, resultList.size());
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
    public void createShouldReturnLogbook() {
        Logbook created = service.create(logbookOne);
        assertEquals("EndOfFishing creation should return entity.", logbookOne, created);

    }

    @Test
    public void removeShouldInvokeEntityManagersRemove() {
        when(em.find(eq(Logbook.class), anyString())).thenReturn(logbookOne);
        service.remove(ID_1);

        verify(em, times(1)).remove(logbookOne);
    }

    @Test
    public void updateShouldReturnLogbook() {
        when(em.find(eq(Logbook.class), anyString())).thenReturn(logbookOne);
        Optional<Logbook> optional = null;
        try {
            optional = service.updateLogbookById(ID_1, logbookOne);
        } catch (TransactionFailedException e) {
            e.printStackTrace();
        }

        verify(em, times(1)).find(eq(Logbook.class), anyString(), any(LockModeType.class));
        assertTrue("Should contain logbook.", optional.isPresent());
    }

    @Test(expected = OptimisticLockException.class)
    public void concurrentUpdateShouldThrowOptimisticLockException() {
        when(em.find(eq(Logbook.class), anyString())).thenReturn(logbookOne);
        Logbook logbookUpdated = new Logbook.LogbookBuilder()
                .withArrival(logbookOne.getArrival())
                .withDeparture(logbookOne.getDeparture())
                .withCatchList(logbookOne.getCatchList())
                .withEndOfFishing(logbookOne.getEndOfFishing())
                .withCommunicationType(logbookOne.getCommunicationType())
                .withId(logbookOne.getId())
                .build();
        when(em.merge(any(Logbook.class))).thenReturn(logbookUpdated);
        Thread first = new Thread(() -> {
            try {
                Optional<Logbook> optional = service.updateLogbookById(ID_1, logbookOne);
            } catch (TransactionFailedException e) {
                e.printStackTrace();
            }
            verify(em, times(1)).find(eq(Logbook.class), anyString());
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        });

        Thread second = new Thread(() -> {
            try {
                Optional<Logbook> optional = service.updateLogbookById(ID_1, logbookOne);
            } catch (TransactionFailedException e) {
                e.printStackTrace();
            }
            verify(em, times(2)).find(eq(Logbook.class), anyString());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        });
        first.run();
        second.run();

    }
    @Test
    public void findByDeparturePortShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(logbookList);

        resultList = service.findByArrivalPort(PORT_DEPARTURE_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(query, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Logbook list size should be 1.", 1, resultList.size());
    }

    @Test
    public void findByArrivalPortShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(logbookList);

        resultList = service.findByArrivalPort(PORT_ARRIVAL_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(query, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Logbook list size should be 1.", 1, resultList.size());
    }

    @Test
    public void findBySpeciesShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(logbookList);

        resultList = service.findBySpecies(SPECIES_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(query, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Logbook list size should be 1.", 1, resultList.size());
    }

    @Test
    public void findWhereCatchWeightIsBiggerShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyLong())).thenReturn(query);
        when(query.getResultList()).thenReturn(logbookList);

        resultList = service.findByWeight(WEIGHT, false);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(query, times(1)).setParameter(anyInt(), anyLong());
        assertEquals("Logbook list size should be 1.", 1, resultList.size());
    }

    @Test
    public void findByDeparturePeriodShouldReturnLogbookList() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(logbookList);

        resultList = service.findByDeparturePeriod(DATE_1.toString(), DATE_2.toString());

        verify(em, times(1)).createNativeQuery(anyString(), eq(Logbook.class));
        verify(query, times(2)).setParameter(anyInt(), anyString());
        assertEquals("Logbook list size should be 1.", 1, resultList.size());
    }

}