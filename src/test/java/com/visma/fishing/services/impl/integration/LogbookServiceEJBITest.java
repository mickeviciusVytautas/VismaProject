package com.visma.fishing.services.impl;

import com.visma.fishing.exception.ConcurrentChangesException;
import com.visma.fishing.exception.EntityNotFoundException;
import com.visma.fishing.messages.Messages;
import com.visma.fishing.model.Arrival;
import com.visma.fishing.model.Catch;
import com.visma.fishing.model.CommunicationType;
import com.visma.fishing.model.Departure;
import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.model.Logbook;
import com.visma.fishing.model.base.BaseEntity;
import com.visma.fishing.services.LogbookService;
import com.visma.fishing.services.Service;
import com.visma.fishing.strategy.SavingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class LogbookServiceEJBITest {

    private static final String PORT_ARRIVAL_1= "port arrival 1";
    private static final String PORT_DEPARTURE_1 = "port departure 1";
    private static final String SPECIES_1 = "species 1";
    private static final String ID_1 = "ID 1";
    private static final Long WEIGHT = 10L;
    private static final Date DATE_1 = new Date();
    private static final Date DATE_2 = new Date();

    @Inject
    LogbookService logbookServiceEJB;
    private Logbook logbookOne;

    @Deployment
    public static Archive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Logbook.class.getPackage().getName())
                .addClass(LogbookService.class)
                .addClass(Service.class)
                .addPackage(SavingStrategy.class.getPackage())
                .addPackage(EntityNotFoundException.class.getPackage())
                .addClass(LogbookServiceEJB.class)
                .addPackage(Logger.class.getPackage())
                .addClass(BaseEntity.class)
                .addClass(Messages.class)
                .addPackage(LogManager.class.getPackage())
                .addPackage(MessageFactory.class.getPackage())
                .addPackage(StatusLogger.class.getPackage())
                .addPackage(SimpleLogger.class.getPackage())
                .addPackage(StringBuilderFormattable.class.getPackage())
                .addPackage(LoggerContextFactory.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("log4j2.properties")
                .addAsResource("application.properties")
                .addPackages(true, "io.xlate.inject")
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Before
    public void before() {
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
    }


    @Test(expected = ConcurrentChangesException.class)
    public void test() {
        logbookServiceEJB.create(logbookOne);
        Optional<Logbook> optionalLogbook = logbookServiceEJB.findById(logbookOne.getId());
        assertTrue(optionalLogbook.isPresent());
        logbookOne.setVersion(1L);
        logbookServiceEJB.updateLogbook(logbookOne);

    }

}
