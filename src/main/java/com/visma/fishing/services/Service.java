package com.visma.fishing.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Service<T, I extends Serializable> {

    List<T> findAll();

    Optional<T> findById(I id);

    void create(T t);

    void remove(I id);
}
