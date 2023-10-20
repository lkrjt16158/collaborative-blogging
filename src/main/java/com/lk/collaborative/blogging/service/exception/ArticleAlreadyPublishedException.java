package com.lk.collaborative.blogging.service.exception;

public class ArticleAlreadyPublishedException extends RuntimeException {
    public ArticleAlreadyPublishedException(String message) {
        super(message);
    }
}
