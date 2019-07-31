package com.visma.fishing.services;

import com.visma.fishing.model.Catch;

import java.util.List;

public interface CatchService extends Service<Catch, String> {

    void updateCatch(Catch aCatch);

    List<Catch> findBySpecies(String species);

    List<Catch> findByWeight(Long weight, boolean bigger);
}
