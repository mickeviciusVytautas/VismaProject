package com.visma.fishing.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
