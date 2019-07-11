package com.visma.fishing.services;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

public interface Service<T, ID extends Serializable> {
    List<T> findAll();
    Response findById(ID id);
    Response create(T t);
    Response update(ID id, T t);
    void remove(ID id);
}
