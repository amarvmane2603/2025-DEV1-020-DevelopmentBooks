package com.bnppf.development_book.exception;

public class UnknownBookException extends RuntimeException {

    public UnknownBookException(String name) {
        super("Unknown book: " + name);
    }
}
