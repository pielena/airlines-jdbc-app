package com.airlines.dao.exception;

public class DaoOperationException extends RuntimeException {
    public DaoOperationException(String message) {
        super(message);
    }
}
