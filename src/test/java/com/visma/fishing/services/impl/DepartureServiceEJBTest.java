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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartureServiceEJBTest {

    private static final String PORT_1 = "port 1";
    private static final String PORT_2 = "port 2";
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

    @Before
    public void init(){
        departure = new Departure(PORT_1, DATE_1);

    }
    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("departure.findAll", Departure.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(departureList);

        departureList.add(departure);

        List<Departure> resultList = service.findAll();
        verify(em, times(1)).createNamedQuery("departure.findAll", Departure.class);

        assertEquals(1, resultList.size());

    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Departure.class), anyString())).thenReturn(departure);

        Response response = service.findById("id");
        assertEquals( 302, response.getStatus());

        when(em.find(eq(Departure.class), anyString())).thenReturn(null);

        response = service.findById("id");
        assertEquals(404, response.getStatus());
    }

    @Test
    public void createShouldReturnCreatedStatusCode() {


        Response response = service.create(departure);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void updateShouldReturnAcceptedStatusCode() {
        when(em.find(eq(Departure.class), anyString())).thenReturn(departure);
        Departure departureTwo = new Departure(PORT_2, DATE_2);

        Response response = service.update("1", departureTwo);

        assertEquals(202, response.getStatus());
    }

    @Test
    public void remove() {
        when(em.find(eq(Departure.class), anyString())).thenReturn(departure);
        service.remove("1");

        verify(em, times(1)).remove(departure);
    }

    @Test
    public void findByPort() {
    }

    @Test
    public void findByPeriod() {
    }
}