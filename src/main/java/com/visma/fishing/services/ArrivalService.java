package com.visma.fishing.services;

import com.visma.fishing.model.Arrival;

import java.util.Date;
import java.util.List;

public interface ArrivalService extends Service<Arrival, String> {

    void updateArrival(Arrival arrival);

    List<Arrival> findByPort(String port);

    List<Arrival> findByPeriod(Date start, Date end);
}
