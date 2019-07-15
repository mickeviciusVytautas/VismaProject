package com.visma.fishing.builder;

import com.visma.fishing.model.CommunicationType;
import com.visma.fishing.model.*;

import java.util.List;

public class LogbookBuilder {

    private Departure departure;
    private EndOfFishing endOfFishing;
    private Arrival arrival;
    private List<Catch> catchList;
    private CommunicationType communicationType;

    public LogbookBuilder withDeparture(Departure departure) {
        this.departure = departure;
        return this;
    }

    public LogbookBuilder withEndOfFishing(EndOfFishing endOfFishing) {
        this.endOfFishing = endOfFishing;
        return this;
    }

    public LogbookBuilder withArrival(Arrival arrival) {
        this.arrival = arrival;
        return this;
    }

    public LogbookBuilder withCatchList(List<Catch> catchList) {
        this.catchList = catchList;
        return this;
    }

    public LogbookBuilder withCommunicationType(CommunicationType communicationType) {
        this.communicationType = communicationType;
        return this;
    }

    public Logbook build() {
        return new Logbook(departure, endOfFishing, arrival, catchList, communicationType.toString());
    }
}