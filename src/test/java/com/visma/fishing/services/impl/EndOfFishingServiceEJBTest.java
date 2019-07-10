package com.visma.fishing.services.impl;

import com.visma.fishing.model.EndOfFishing;
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
public class EndOfFishingServiceEJBTest {

    private static final Date DATE_1 = new Date(2016 - 5 - 1);
    private static final Date DATE_2 = new Date(2016 - 6 - 1);

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<EndOfFishing> query;

    @InjectMocks
    private EndOfFishingServiceEJB service;

    private EndOfFishing endOfFishing = new EndOfFishing();

    private List<EndOfFishing> endOfFishingList = new ArrayList<>();

    @Before
    public void init(){
        endOfFishing = new EndOfFishing(DATE_1);

    }
    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("endOfFishing.findAll", EndOfFishing.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(endOfFishingList);

        endOfFishingList.add(endOfFishing);

        List<EndOfFishing> resultList = service.findAll();
        verify(em, times(1)).createNamedQuery("endOfFishing.findAll", EndOfFishing.class);

        assertEquals(1, resultList.size());
    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(EndOfFishing.class), anyString())).thenReturn(endOfFishing);

        Response response = service.findById("id");
        assertEquals( 302, response.getStatus());

        when(em.find(eq(EndOfFishing.class), anyString())).thenReturn(null);

        response = service.findById("id");
        assertEquals(404, response.getStatus());
    }

    @Test
    public void createShouldReturnCreatedStatusCode() {


        Response response = service.create(endOfFishing);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void updateShouldReturnAcceptedStatusCode() {
        when(em.find(eq(EndOfFishing.class), anyString())).thenReturn(endOfFishing);
        EndOfFishing departureTwo = new EndOfFishing(DATE_2);

        Response response = service.update("1", departureTwo);

        assertEquals(202, response.getStatus());
    }

    @Test
    public void remove() {
        when(em.find(eq(EndOfFishing.class), anyString())).thenReturn(endOfFishing);
        service.remove("1");

        verify(em, times(1)).remove(endOfFishing);

    }

    @Test
    public void findByPort() {
    }

    @Test
    public void findByPeriod() {
    }
}