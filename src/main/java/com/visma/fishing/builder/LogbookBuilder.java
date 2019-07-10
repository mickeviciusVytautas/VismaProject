package com.visma.fishing.builder;

import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.model.*;

import java.util.List;

public class LogbookBuilder {

    private Departure departure;
    private EndOfFishing endOfFishing;
    private Arrival arrival;
    private List<Catch> catchList;
    private ConnectionType connectionType;

    public LogbookBuilder setDeparture(Departure departure) {
        this.departure = departure;
        return this;
    }

    public LogbookBuilder setEndOfFishing(EndOfFishing endOfFishing) {
        this.endOfFishing = endOfFishing;
        return this;
    }

    public LogbookBuilder setArrival(Arrival arrival) {
        this.arrival = arrival;
        return this;
    }

    public LogbookBuilder setCatchList(List<Catch> catchList) {
        this.catchList = catchList;
        return this;
    }

    public LogbookBuilder setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public Logbook build() {
        return new Logbook(departure, endOfFishing, arrival, catchList, connectionType.toString());
    }
}