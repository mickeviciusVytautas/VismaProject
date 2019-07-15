package com.visma.fishing.services;

import com.visma.fishing.model.Catch;

import java.util.List;
import java.util.Optional;

public interface CatchService extends Service<Catch, String> {
    Optional<Catch> updateCatchById(String id, Catch aCatch);
    List<Catch> findBySpecies(String species);
    List<Catch> findByWeight(Long weight, boolean bigger);
}
