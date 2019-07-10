package com.visma.fishing.services;

import com.visma.fishing.model.EndOfFishing;

import java.util.List;

public interface EndOfFishingService extends Service<EndOfFishing, String>{
    List<EndOfFishing> findByPeriod(String start, String end);

}
