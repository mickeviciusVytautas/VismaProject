package com.visma.fishing.services;

import com.visma.fishing.model.Arrival;

import java.util.List;

public interface ArrivalService extends Service<Arrival, String> {

    List<Arrival> findByPort(String port);
    List<Arrival> findByPeriod(String start, String end);
}
