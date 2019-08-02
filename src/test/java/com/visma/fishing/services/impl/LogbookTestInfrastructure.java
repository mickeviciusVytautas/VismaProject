package com.visma.fishing.services.impl;

import com.visma.fishing.model.Arrival;
import com.visma.fishing.model.Catch;
import com.visma.fishing.model.CommunicationType;
import com.visma.fishing.model.Departure;
import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.model.Logbook;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

public class TestInfrastructure {

    public static final String PORT_ARRIVAL_1= "port arrival 1";
    public static final String PORT_DEPARTURE_1 = "port departure 1";
    public static final String SPECIES_1 = "species 1";
    public static final String ID_1 = "ID 1";
    public static final Long WEIGHT = 10L;
    public static final Date DATE_1 = new Date();
    public static final Date DATE_2 = new Date();

    @Mock
    public EntityManager em;

    public Logbook logbookOne;

    @InjectMocks
    public LogbookServiceEJB service;

    public List<Logbook> logbooks = new ArrayList<>();

    public List<Logbook> results = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public TypedQuery<Logbook> query = (TypedQuery<Logbook>) mock(TypedQuery.class);

    @Before
    public void init() {
        Logbook.LogbookBuilder logbookBuilder = new Logbook.LogbookBuilder();
        Departure departure = new Departure(PORT_DEPARTURE_1, DATE_1);
        EndOfFishing endOfFishing = new EndOfFishing(DATE_2);
        Arrival arrival = new Arrival(PORT_ARRIVAL_1, DATE_1);
        Catch aCatch = new Catch(SPECIES_1, WEIGHT);
        List<Catch> catchList = new ArrayList<Catch>(){{add(aCatch);}};
        logbookOne = logbookBuilder
                .withArrival(arrival)
                .withDeparture(departure)
                .withEndOfFishing(endOfFishing)
                .withCatchList(catchList)
                .withCommunicationType(CommunicationType.NETWORK)
                .build();
        logbookOne.setVersion(0L);
        logbooks.add(logbookOne);

    }

}
