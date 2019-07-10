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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrivalServiceEJBTest {

    private static final String PORT_1 = "port 1";
    private static final String PORT_2 = "port 2";
    private static final Date DATE_1 = new Date(2016 - 5 - 1);
    private static final Date DATE_2 = new Date(2016 - 6 - 1);

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Arrival> query;

    @InjectMocks
    private ArrivalServiceEJB service;

    private Arrival arrival = new Arrival();

    private List<Arrival> arrivalList = new ArrayList<>();

    @Before
    public void init(){
        arrival.setPort(PORT_1);
        arrival.setDate(DATE_1);

    }
    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("arrival.findAll", Arrival.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(arrivalList);

        arrivalList.add(arrival);

        List<Arrival> resultList = service.findAll();
        verify(em, times(1)).createNamedQuery("arrival.findAll", Arrival.class);
        assertEquals(1, resultList.size());
    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Arrival.class), anyString())).thenReturn(arrival);

        Response response = service.findById("id");
        assertEquals( 302, response.getStatus());

        when(em.find(eq(Arrival.class), anyString())).thenReturn(null);

        response = service.findById("id");
        assertEquals(404, response.getStatus());
    }

    @Test
    public void createShouldReturnCreatedStatusCode() {
        Response response = service.create(arrival);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void updateShouldReturnAcceptedStatusCode() {
        when(em.find(eq(Arrival.class), anyString())).thenReturn(arrival);
        Arrival arrivalTwo = new Arrival(PORT_2, DATE_2);
        Response response = service.update("1", arrivalTwo);

        assertEquals(202, response.getStatus());
    }

    @Test
    public void remove() {
        when(em.find(eq(Arrival.class), anyString())).thenReturn(arrival);
        service.remove("1");

        verify(em, times(1)).remove(arrival);
    }

    @Test
    public void findByPort() {
    }

    @Test
    public void findByPeriod() {
    }
}