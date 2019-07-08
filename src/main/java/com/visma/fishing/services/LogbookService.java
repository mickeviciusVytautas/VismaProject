package com.visma.fishing.services;

import com.visma.fishing.model.Logbook;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public interface LogbookService {
    List<Logbook> findAll();
    Optional<Logbook> findById(Long id);
    Response create(Logbook logbook);
    Response createBySatellite(Logbook logbook);
    void update(Long id, Logbook logbook);
    void remove(Long id);

    void findByDeparturePort(String portName);

}
