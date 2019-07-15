package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;

import javax.ws.rs.core.Response;

public interface SavingStrategy {

    void save(Logbook logbook);

}
