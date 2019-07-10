package com.visma.fishing.services;

import com.visma.fishing.model.Logbook;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LogbookService {
    List<Logbook> findAll();
    Optional<Logbook> findById(String id);
    Response create(Logbook logbook);
    Response createBySatellite(Logbook logbook);
    void update(Long id, Logbook logbook);
    void remove(Long id);

    List<Logbook> findByDeparturePort(String portName);
    List<Logbook> findByArrivalPort(String port);
    List<Logbook> findBySpecies(String species);
    List<Logbook> findWhereCatchWeightIsBigger(Long weight);
    List<Logbook> findByDeparturePeriod(Date start, Date end);

}
