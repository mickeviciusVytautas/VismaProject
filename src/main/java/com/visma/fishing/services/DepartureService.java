package com.visma.fishing.services;

import com.visma.fishing.model.Departure;

import java.util.List;
import java.util.Optional;

public interface DepartureService extends Service<Departure, String> {
    Optional<Departure> updateDepartureById(String id, Departure departure);
    List<Departure> findByPort(String port);
    List<Departure> findByPeriod(String start, String end);
}
