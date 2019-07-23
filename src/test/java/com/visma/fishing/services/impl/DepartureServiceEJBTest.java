package com.visma.fishing.services.impl;


import com.visma.fishing.model.Departure;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartureServiceEJBTest {

    private static final String PORT_1 = "port 1";
    private static final String PORT_2 = "port 2";
    private static final String ID_1 = "ID 1";
    private static final Date DATE_1 = new Date(2016 - 5 - 1);
    private static final Date DATE_2 = new Date(2016 - 6 - 1);

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Departure> query;

    @InjectMocks
    private DepartureServiceEJB service;

    private Departure departure = new Departure();

    private List<Departure> departureList = new ArrayList<>();

    private List<Departure> resultList = new ArrayList<>();

    @Before
    public void init(){
        departure = new Departure(PORT_1, DATE_1);
        departureList.add(departure);

    }
    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("departure.findAll", Departure.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(departureList);

        resultList = service.findAll();

        verify(em, times(1)).createNamedQuery("departure.findAll", Departure.class);
        assertEquals("Size of departure list should be 1.", 1, resultList.size());

    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Departure.class), anyString())).thenReturn(departure);

        Optional<Departure> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(Departure.class), anyString());
        assertTrue("Should contain departure.", optional.isPresent());
        assertEquals("Should contain departure.", departure, optional.get());
    }

    @Test
    public void createShouldInvokeEMPersist() {
        service.create(departure);
        verify(em, times(1)).persist(any(Departure.class));

    }

    @Test
    public void removeShouldInvokeEntityManagersRemove() {
        when(em.find(eq(Departure.class), anyString())).thenReturn(departure);
        service.remove(ID_1);

        verify(em, times(1)).remove(departure);
    }

    @Test
    public void updateShouldReturnAcceptedStatusCode() {
        when(em.find(eq(Departure.class), anyString())).thenReturn(departure);

        Optional<Departure> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(Departure.class), anyString());
        assertTrue("Should contain departure.", optional.isPresent());
        assertEquals("Should contain departure.", departure, optional.get());
    }

    @Test
    public void findByPortShouldReturnDepartureList() {
        when(em.createNativeQuery(anyString(), eq(Departure.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(departureList);

        resultList = service.findByPort(PORT_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Departure.class));
        verify(query, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Departure list size should be 1.", 1, resultList.size());
    }

    @Test
    public void findByPeriodShouldReturnDepartureList() {
        when(em.createNativeQuery(anyString(), eq(Departure.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(departureList);

        resultList = service.findByPeriod(DATE_1.toString(), DATE_2.toString());

        verify(em, times(1)).createNativeQuery(anyString(), eq(Departure.class));
        verify(query, times(2)).setParameter(anyInt(), anyString());
        assertEquals("Departure list size should be 1.", 1, resultList.size());
    }
}