package com.visma.fishing.exception;

import java.text.MessageFormat;

public class TransactionFailedException extends Exception {

    private final String message;

    public TransactionFailedException(String logbookConcurrentMsg, String id) {
        super();
        this.message = MessageFormat.format(logbookConcurrentMsg, id);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
