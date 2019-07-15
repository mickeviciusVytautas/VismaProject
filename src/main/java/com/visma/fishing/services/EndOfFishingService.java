package com.visma.fishing.services;

import com.visma.fishing.model.EndOfFishing;

import java.util.List;
import java.util.Optional;

public interface EndOfFishingService extends Service<EndOfFishing, String>{
    Optional<EndOfFishing> updateEndOfFishingById(String id, EndOfFishing endOfFishing);
    List<EndOfFishing> findByPeriod(String start, String end);
}
