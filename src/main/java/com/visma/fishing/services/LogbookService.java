package com.visma.fishing.services;

import com.visma.fishing.model.Logbook;

import java.util.List;

public interface LogbookService extends Service<Logbook, String>{
    List<Logbook> findByDeparturePort(String portName);
    List<Logbook> findByArrivalPort(String port);
    List<Logbook> findBySpecies(String species);
    List<Logbook> findByWeight(Long weight, boolean searchWithLowerWeight);
    List<Logbook> findByDeparturePeriod(String start, String end);

}
