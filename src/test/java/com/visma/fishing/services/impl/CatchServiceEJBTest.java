package com.visma.fishing.services.impl;

import com.visma.fishing.model.Arrival;
import com.visma.fishing.model.Catch;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatchServiceEJBTest {

    private static final String SPECIES_1 = "species 1";
    private static final String SPECIES_2 = "SPECIES 2";
    private static final Long WEIGHT_1 = 10L;
    private static final Long WEIGHT_2 = 100L;

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Catch> query;

    @InjectMocks
    private CatchServiceEJB service;

    private Catch aCatch;

    private List<Catch> catchList = new ArrayList<>();

    @Before
    public void init(){
        aCatch = new Catch(SPECIES_1, WEIGHT_1);
    }
    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("catch.findAll", Catch.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(catchList);

        catchList.add(aCatch);

        List<Catch> resultList = service.findAll();

        assertEquals(1, resultList.size());

        catchList.add(aCatch);

        assertEquals(2, resultList.size());
    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Catch.class), anyString())).thenReturn(aCatch);

        Response response = service.findById("id");
        assertEquals( 302, response.getStatus());

        when(em.find(eq(Catch.class), anyString())).thenReturn(null);

        response = service.findById("id");
        assertEquals(404, response.getStatus());
    }

    @Test
    public void createShouldReturnCreatedStatusCode() {
        Response response = service.create(aCatch);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void updateShouldReturnAcceptedStatusCode() {
        when(em.find(eq(Catch.class), anyString())).thenReturn(aCatch);
        Catch catchTwo = new Catch(SPECIES_2, WEIGHT_2);
        Response response = service.update("1", catchTwo);

        assertEquals(202, response.getStatus());
    }

    //    TODO: How to test void remove?
    @Test
    public void remove() {
        when(em.find(eq(Catch.class), anyString())).thenReturn(aCatch);
        service.remove("1");

    }

    @Test
    public void findByPort() {
    }

    @Test
    public void findByPeriod() {
    }
}