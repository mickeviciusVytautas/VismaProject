package com.visma.fishing.services.impl;

import com.visma.fishing.model.Arrival;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrivalServiceEJBTest {

    private static final String PORT_1 = "port 1";
    private static final String ID_1 = "ID 1";
    private static final Date DATE_1 = new Date(2016 - 5 - 1);
    private static final Date DATE_2 = new Date(2016 - 6 - 1);

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Arrival> query;

    @InjectMocks
    private ArrivalServiceEJB service;

    private Arrival arrival = new Arrival();

    private List<Arrival> arrivals = new ArrayList<>();

    private List<Arrival> results;

    @Before
    public void init(){
        arrival = new Arrival(PORT_1, DATE_1);
        arrivals.add(arrival);
    }

    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("arrival.findAll", Arrival.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(arrivals);

        results = service.findAll();

        verify(em, times(1)).createNamedQuery("arrival.findAll", Arrival.class);
        assertEquals("Size of arrival list should be 1.", 1, results.size());
    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Arrival.class), anyString())).thenReturn(arrival);

        Optional<Arrival> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(Arrival.class), anyString());
        assertTrue("Should contain arrival.", optional.isPresent());
        assertEquals("Should contain arrival.", arrival, optional.get());
    }

    @Test
    public void createShouldReturnCreatedStatusCode() {
        Arrival created = service.create(arrival);
        assertEquals("Arrival creation should return entity.", arrival, created);
    }

    @Test
    public void removeShouldInvokeEntityManagersRemove() {
        when(em.find(eq(Arrival.class), anyString())).thenReturn(arrival);
        service.remove(ID_1);

        verify(em, times(1)).remove(arrival);
    }

    @Test
    public void updateShouldReturnAcceptedStatusCode() {
        when(em.find(eq(Arrival.class), anyString())).thenReturn(arrival);
        Optional<Arrival> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(Arrival.class), anyString());
        assertTrue("Should contain arrival.", optional.isPresent());
        assertEquals("Should contain arrival.", arrival, optional.get());
    }

    @Test
    public void findByPortShouldReturnArrivalList() {
        when(em.createNativeQuery(anyString(), eq(Arrival.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(arrivals);

        results = service.findByPort(PORT_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Arrival.class));
        verify(query, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Arrival list size should be 1.", 1, results.size());
    }

    @Test
    public void findByPeriodShouldReturnArrivalList() {
        when(em.createNativeQuery(anyString(), eq(Arrival.class))).thenReturn(query);
        when(query.setParameter(anyInt(), any(Date.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(arrivals);

        results = service.findByPeriod(DATE_1, DATE_2);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Arrival.class));
        verify(query, times(2)).setParameter(anyInt(), any(Date.class));
        assertEquals("Arrival list size should be 1.", 1, results.size());
    }
}