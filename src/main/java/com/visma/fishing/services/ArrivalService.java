package com.visma.fishing.services;

import com.visma.fishing.model.Arrival;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ArrivalService extends Service<Arrival, String> {
    Optional<Arrival> updateArrivalById(String id, Arrival arrival);
    List<Arrival> findByPort(String port);

    List<Arrival> findByPeriod(Date start, Date end);
}
