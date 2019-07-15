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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

        Optional<Catch> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(Catch.class), anyString());
        assertTrue("Should contain catch.", optional.isPresent());
        assertEquals("Should contain catch.", aCatch, optional.get());
    }

    @Test
    public void createShouldReturnCatch() {
        Catch created = service.create(aCatch);
        assertEquals("Catch creation should return entity.", aCatch, created);
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
        Optional<Catch> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(Catch.class), anyString());
        assertTrue("Should contain catch.", optional.isPresent());
        assertEquals("Should contain catch.", aCatch, optional.get());
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