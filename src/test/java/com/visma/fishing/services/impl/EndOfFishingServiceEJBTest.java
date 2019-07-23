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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EndOfFishingServiceEJBTest {

    private static final Date DATE_1 = new Date(2016 - 5 - 1);
    private static final Date DATE_2 = new Date(2016 - 6 - 1);
    private static final String ID_1 = "ID 1";

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<EndOfFishing> query;

    @InjectMocks
    private EndOfFishingServiceEJB service;

    private EndOfFishing endOfFishing = new EndOfFishing();

    private List<EndOfFishing> endOfFishingList = new ArrayList<>();

    private List<EndOfFishing> resultList = new ArrayList<>();

    @Before
    public void init(){
        endOfFishing = new EndOfFishing(DATE_1);
        endOfFishingList.add(endOfFishing);

    }
    @Test
    public void findAllShouldReturnCorrectListSize() {
        when(em.createNamedQuery("endOfFishing.findAll", EndOfFishing.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(endOfFishingList);

        resultList = service.findAll();

        verify(em, times(1)).createNamedQuery("endOfFishing.findAll", EndOfFishing.class);
        assertEquals("Size of endOfFishing list should be 1.", 1, resultList.size());
    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(EndOfFishing.class), anyString())).thenReturn(endOfFishing);

        Optional<EndOfFishing> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(EndOfFishing.class), anyString());
        assertTrue("Should contain endOfFishing.", optional.isPresent());
        assertEquals("Should contain endOfFishing.", endOfFishing, optional.get());
    }

    @Test
    public void createShouldInvokeEMPersist() {
        service.create(endOfFishing);
        verify(em, times(1)).persist(any(EndOfFishing.class));

    }

    @Test
    public void removeShouldInvokeEntityManagersRemove() {
        when(em.find(eq(EndOfFishing.class), anyString())).thenReturn(endOfFishing);
        service.remove(ID_1);

        verify(em, times(1)).remove(endOfFishing);

    }

    @Test
    public void updateShouldReturnAcceptedStatusCode() {
        when(em.find(eq(EndOfFishing.class), anyString())).thenReturn(endOfFishing);

        Optional<EndOfFishing> optional = service.findById(ID_1);

        verify(em, times(1)).find(eq(EndOfFishing.class), anyString());
        assertTrue("Should contain endOfFishing.", optional.isPresent());
        assertEquals("Should contain endOfFishing.", endOfFishing, optional.get());

    }

    @Test
    public void findByPeriodShouldReturnEndOfFishingList() {
        when(em.createNativeQuery(anyString(), eq(EndOfFishing.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(endOfFishingList);

        resultList = service.findByPeriod(DATE_1.toString(), DATE_2.toString());

        verify(em, times(1)).createNativeQuery(anyString(), eq(EndOfFishing.class));
        verify(query, times(2)).setParameter(anyInt(), anyString());
        assertEquals("EndOFFishing list size should be 2.", 1, resultList.size());

    }
}