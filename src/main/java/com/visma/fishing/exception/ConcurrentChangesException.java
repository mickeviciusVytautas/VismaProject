package com.visma.fishing.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ConcurrentChangesException extends RuntimeException {

    public ConcurrentChangesException(String message) {
        super(message);
    }
}
