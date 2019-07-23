package com.visma.fishing.services;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Service<T, ID extends Serializable> {
    List<T> findAll();

    Optional<T> findById(ID id);

    void create(T t);
    void remove(ID id);
}
