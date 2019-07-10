package com.visma.fishing.services;

import com.visma.fishing.model.EndOfFishing;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public interface EndOfFishingService {
    List<EndOfFishing> findAll();
    Optional<EndOfFishing> findById(String id);
    Response create(EndOfFishing endOfFishing);
    void update(Long id, EndOfFishing endOfFishing);
    void remove(Long id);
}
