package com.visma.fishing.services;

import com.visma.fishing.model.Departure;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public interface DepartureService {
    List<Departure> findAll();
    Optional<Departure> findById(String id);
    Response create(Departure departure);
    void update(Long id, Departure departure);
    void remove(Long id);
}
