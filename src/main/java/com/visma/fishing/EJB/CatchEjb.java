package com.visma.fishing.EJB;

import com.visma.fishing.model.Catch;

import java.util.List;

public interface CatchEjb {
    List<Catch> findAll();
    Catch findById(Long id);
    void create(Catch aCatch);
    void update(Long id, Catch aCatch);
    void remove(Long id);
}
