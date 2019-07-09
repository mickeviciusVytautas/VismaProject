package com.visma.fishing.services.impl;

import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import sun.rmi.runtime.Log;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LogbookServiceEJBTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private LogbookServiceEJB service;

    @Before
    public void init() {
        Departure departure = new Departure(new Date(), "p1");
        Arrival arrival = new Arrival("p2", new Date());
        EndOfFishing endOfFishing = new EndOfFishing(new Date());
        Catch aCatch = new Catch("s", 10L);
        List<Catch> catchList = new ArrayList<Catch>(){{add(aCatch);}};
        Logbook logbook = new Logbook(departure, catchList, arrival, endOfFishing, ConnectionType.NETWORK.toString());
        Optional<Logbook> optionalLogbook = Optional.of(logbook);
        Mockito.when(em.find(eq(Logbook.class), anyString())).thenReturn(logbook);
    }

    @Test
    public void find() {
        Logbook logbook =  service.findById(1L).ged .
                t();
        assertNotNull( "Logbook by Id was not found", service.findById(1L));


    }
}