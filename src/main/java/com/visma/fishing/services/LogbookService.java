package com.visma.fishing.services;

import com.visma.fishing.exception.TransactionFailedException;
import com.visma.fishing.model.Logbook;

import java.util.List;
import java.util.Optional;

public interface LogbookService extends Service<Logbook, String>{
    Optional<Logbook> updateLogbookById(String id, Logbook logbook) throws TransactionFailedException;

    List<Logbook> saveAll(List<Logbook> logbooks);
    List<Logbook> findByDeparturePort(String portName);
    List<Logbook> findByArrivalPort(String port);
    List<Logbook> findBySpecies(String species);
    List<Logbook> findByWeight(Long weight, boolean searchWithLowerWeight);
    List<Logbook> findByDeparturePeriod(String start, String end);

}
