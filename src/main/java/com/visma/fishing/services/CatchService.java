package com.visma.fishing.services;

import com.visma.fishing.model.Catch;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public interface CatchService {
    List<Catch> findAll();
    Optional<Catch> findById(Long id);
    Response create(Catch aCatch);
    void update(Long id, Catch aCatch);
    void remove(Long id);
}
