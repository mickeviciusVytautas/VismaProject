package com.visma.fishing.EJB;

import com.visma.fishing.model.Arrival;

import java.util.List;

public interface ArrivalEJB {
    List findAll();
    Arrival findById(Long id);
    void create(Arrival arrival);
    void update(Long id, Arrival arrival);
    void remove(Long id);
}
