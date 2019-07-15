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

    private List<Arrival> arrivalList = new ArrayList<>();

    private List<Arrival> resultList;

    @Before
    public void init(){
        arrival = new Arrival(PORT_1, DATE_1);
        arrivalList.add(arrival);
    }

    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("arrival.findAll", Arrival.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(arrivalList);

        resultList = service.findAll();

        verify(em, times(1)).createNamedQuery("arrival.findAll", Arrival.class);
        assertEquals("Size of arrival list should be 1.",1, resultList.size());
    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Arrival.class), anyString())).thenReturn(arrival);

        Response response = service.findById(ID_1);
        verify(em, times(1)).find(eq(Arrival.class), anyString());
        assertEquals("FindById arrival should return status code FOUND", Response.Status.FOUND.getStatusCode(), response.getStatus());

        when(em.find(eq(Arrival.class), anyString())).thenReturn(null);

        response = service.findById(ID_1);
        verify(em, times(2)).find(eq(Arrival.class), anyString());
        assertEquals("FindById arrival should return status code NOT_FOUND", Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void createShouldReturnCreatedStatusCode() {
        Response response = service.create(arrival);
        assertEquals("Arrival creation should return status code CREATED.", Response.Status.CREATED.getStatusCode(), response.getStatus());
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
        Response response = service.update(ID_1, arrival);

        verify(em, times(1)).merge(any(Arrival.class));
        assertEquals("Arrival update returned incorrect status code", Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
    }

    @Test
    public void findByPortShouldReturnArrivalList() {
        when(em.createNativeQuery(anyString(), eq(Arrival.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(arrivalList);

        resultList = service.findByPort(PORT_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Arrival.class));
        verify(query, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Arrival list size should be 1.", 1, resultList.size());
    }

    @Test
    public void findByPeriodShouldReturnArrivalList() {
        when(em.createNativeQuery(anyString(), eq(Arrival.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(arrivalList);

        resultList = service.findByPeriod(DATE_1.toString(), DATE_2.toString());

        verify(em, times(1)).createNativeQuery(anyString(), eq(Arrival.class));
        verify(query, times(2)).setParameter(anyInt(), anyString());
        assertEquals("Arrival list size should be 1.", 1, resultList.size());
    }
}