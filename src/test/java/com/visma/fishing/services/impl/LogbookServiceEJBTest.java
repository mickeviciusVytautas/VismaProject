package com.visma.fishing.services.impl;

import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.model.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogbookServiceEJBTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private LogbookServiceEJB service;

    private Logbook logbook;

    private List<Logbook> logbookList = new ArrayList<>();

    @Before
    public void init() {
        Departure departure = new Departure(new Date(), "p1");
        Arrival arrival = new Arrival("p2", new Date());
        EndOfFishing endOfFishing = new EndOfFishing(new Date());
        Catch aCatch = new Catch("s", 10L);
        List<Catch> catchList = new ArrayList<Catch>(){{add(aCatch);}};
        logbook = new Logbook(departure, catchList, arrival, endOfFishing, ConnectionType.NETWORK.toString());

       logbookList.add(logbook);

    }

    @Test
    public void shouldFindCreatedLogbook() {

        when(em.find(eq(Logbook.class), anyString())).thenReturn(logbook);

        Optional<Logbook> optionalLogbook = service.findById("asdasd");
        assertTrue( "Logbook is not present", optionalLogbook.isPresent());

    }

    @Test
    public void shouldCreateLogbook() {

        doNothing().when(em).persist(any(Logbook.class));

        Response response = service.create(logbook);
        assertEquals(200, response.getStatus());
    }

    @Test
     public void shouldFindAllLogbooks() {

        doReturn(logbookList).when(em).createNamedQuery("logbook.findAll", Logbook.class).getResultList();

        assertEquals(1, logbookList.size());

        logbookList.add(logbook);

        assertEquals(2, logbookList.size());
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByDeparturePort() {
    }

    @Test
    public void findByArrivalPort() {
    }

    @Test
    public void findBySpecies() {
    }

    @Test
    public void findWhereCatchWeightIsBigger() {
    }

    @Test
    public void findByDeparturePeriod() {
    }

    @Test
    public void create() {
    }

    @Test
    public void createBySatellite() {
    }

    @Test
    public void update() {
    }

    @Test
    public void remove() {
    }
}