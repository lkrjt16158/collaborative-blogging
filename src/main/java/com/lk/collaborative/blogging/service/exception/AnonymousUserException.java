package com.lk.collaborative.blogging.service.exception;

public class AnonymousUserException extends RuntimeException {
    public AnonymousUserException(String message) {
        super(message);
    }
}
