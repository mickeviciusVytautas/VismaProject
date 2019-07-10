package com.visma.fishing.services;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/*
 * Thanks to Julius Cerniauskas for suggestion
 */
public interface Service<T, ID extends Serializable> {
    List<T> findAll();
    Response findById(ID id);
    Response create(T t);
    Response update(ID id, T t);
    void remove(ID id);
}
