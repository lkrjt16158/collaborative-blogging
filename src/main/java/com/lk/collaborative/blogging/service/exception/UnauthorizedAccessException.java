package com.lk.collaborative.blogging.service.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
    public UnauthorizedAccessException(Exception e) {
        super(e);
    }
}
