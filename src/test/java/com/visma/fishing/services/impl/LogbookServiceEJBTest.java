package com.visma.fishing.services.impl;

import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.builder.LogbookBuilder;
import com.visma.fishing.model.*;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogbookServiceEJBTest {

    private static final String PORT_ARRIVAL_1= "port arrival 1";
    private static final String PORT_DEPARTURE_1 = "port departure 1";
    private static final String SPECIES_1 = "species 1";
    private static final Date DATE_1 = new Date(2019 - 5 - 1);
    private static final Date DATE_2 = new Date(2019 - 6 - 1);
    private static final Date DATE_3 = new Date(2019 - 7 - 1);

    @Mock
    private EntityManager em;

    @InjectMocks
    private LogbookServiceEJB service;

    private Logbook logbookOne;

    private Logbook logbookTwo;

    private List<Logbook> logbookList = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private
    TypedQuery<Logbook> query = (TypedQuery<Logbook>) mock(TypedQuery.class);
    @Before
    public void init() {
        LogbookBuilder logbookBuilder = new LogbookBuilder();
        Departure departure = new Departure(PORT_DEPARTURE_1, DATE_1);
        EndOfFishing endOfFishing = new EndOfFishing(DATE_2);
        Arrival arrival = new Arrival(PORT_ARRIVAL_1, DATE_3);
        Catch aCatch = new Catch("s", 10L);
        List<Catch> catchList = new ArrayList<Catch>(){{add(aCatch);}};
        logbookOne = logbookBuilder
                .setArrival(arrival)
                .setDeparture(departure)
                .setEndOfFishing(endOfFishing)
                .setCatchList(catchList)
                .setConnectionType(ConnectionType.NETWORK)
                .build();

        logbookList.add(logbookOne);

    }

    @Test
    public void findByIdShouldReturnExistingLogbook() {

        when(em.find(eq(Logbook.class), anyString())).thenReturn(logbookOne);

        Optional<Logbook> optionalLogbook = service.findById("asdasd");
        assertTrue( "Logbook is not present", optionalLogbook.isPresent());

    }

    @Test
    public void createShouldReturnOkStatusCode() {

        doNothing().when(em).persist(eq(Logbook.class));

        Response response = service.create(logbookOne);
        assertEquals("Logbook creation by NETWORK status is incorrect",201, response.getStatus());

        logbookOne.setConnectionType(ConnectionType.SATELLITE);

        Response responseSatellite = service.create(logbookOne);
        assertEquals("Logbook creation by SATELLITE status is incorrect", 201, responseSatellite.getStatus());
    }

    @Test
     public void findAllShouldReturnLogbookList() {

        when(em.createNamedQuery("logbook.findAll", Logbook.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(logbookList);

        assertEquals("Incorrect size of logbooks is found", 1, logbookList.size());

        logbookList.add(logbookOne);

        assertEquals("Incorrect size of logbooks is found", 2, logbookList.size());
    }

@Ignore
    @Test
    public void findByDeparturePort() {

        when(em.createNativeQuery(anyString(), eq(Logbook.class)).setParameter(1, PORT_DEPARTURE_1)).thenReturn(query);

        when(query.getResultList()).thenReturn(logbookList);
        logbookList = service.findByDeparturePort(PORT_DEPARTURE_1);

        assertEquals(1, logbookList.size());

    }
@Ignore
    @Test
    public void findByArrivalPort() {
        when(em.createNativeQuery(anyString(), eq(Logbook.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(logbookList);
        logbookList = service.findByArrivalPort(PORT_ARRIVAL_1);

        assertEquals(1, logbookList.size());
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
    public void createBySatellite() {
    }

    @Test
    public void update() {

        service.update(1L, logbookOne);
    }

    @Test
    public void remove() {
    }
}