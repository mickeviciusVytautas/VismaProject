package com.visma.fishing.services.impl;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CatchServiceEJBTest {

    private static final String SPECIES_1 = "species 1";
    private static final String SPECIES_2 = "SPECIES 2";
    private static final String ID_1 = "ID 1";
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

    private List<Catch> resultList = new ArrayList<>();

    @Before
    public void init(){
        aCatch = new Catch(SPECIES_1, WEIGHT_1);
        catchList.add(aCatch);

    }
    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("catch.findAll", Catch.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(catchList);

        resultList = service.findAll();

        verify(em, times(1)).createNamedQuery("catch.findAll", Catch.class);
        assertEquals("Size of catch list should be 1.", 1, resultList.size());

    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Catch.class), anyString())).thenReturn(aCatch);

        Response response = service.findById(ID_1);
        verify(em, times(1)).find(eq(Catch.class), anyString());
        assertEquals("FindById catch should return status code FOUND", Response.Status.FOUND.getStatusCode(), response.getStatus());

        when(em.find(eq(Catch.class), anyString())).thenReturn(null);

        response = service.findById(ID_1);
        verify(em, times(2)).find(eq(Catch.class), anyString());
        assertEquals("FindById catch should return status code NOT_FOUND", Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void createShouldReturnCreatedStatusCode() {
        Response response = service.create(aCatch);
        assertEquals("Catch creation should return status code CREATED.", Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void remove() {
        when(em.find(eq(Catch.class), anyString())).thenReturn(aCatch);
        service.remove(ID_1);

        verify(em, times(1)).remove(aCatch);

    }

    @Test
    public void updateShouldReturnAcceptedStatusCode() {
        when(em.find(eq(Catch.class), anyString())).thenReturn(aCatch);
        Catch catchTwo = new Catch(SPECIES_2, WEIGHT_2);
        Response response = service.update(ID_1, aCatch);

        verify(em, times(1)).merge(any(Catch.class));
        assertEquals("Catch update returned incorrect status code", Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
    }

    @Test
    public void findBySpeciesShouldReturnCatchList() {
        when(em.createNativeQuery(anyString(), eq(Catch.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(catchList);

        resultList = service.findBySpecies(SPECIES_1);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Catch.class));
        verify(query, times(1)).setParameter(anyInt(), anyString());
        assertEquals("Species list size should be 1.", 1, resultList.size());
    }

    @Test
    public void findByWeight() {
        when(em.createNativeQuery(anyString(), eq(Catch.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyLong())).thenReturn(query);
        when(query.getResultList()).thenReturn(catchList);

        resultList = service.findByWeight(WEIGHT_1, false);

        verify(em, times(1)).createNativeQuery(anyString(), eq(Catch.class));
        verify(query, times(1)).setParameter(anyInt(), anyLong());
        assertEquals("Species list size should be 1.", 1, resultList.size());
    }
}