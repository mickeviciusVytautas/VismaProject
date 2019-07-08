package com.visma.fishing.services;

import com.visma.fishing.model.Arrival;
import com.visma.fishing.model.Catch;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public interface ArrivalService {
    List<Arrival> findAll();
    Optional<Arrival> findById(Long id);
    Response create(Arrival arrival);
    Response update(Long id, Arrival arrival);
    void remove(Long id);
}
