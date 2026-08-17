package com.bnppf.development_book.service;

import com.bnppf.development_book.exception.UnknownBookException;
import com.bnppf.development_book.model.Book;

public final class BookCatalog {

    public Book findByName(String name) {
        try {
            return Book.valueOf(name);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UnknownBookException(name);
        }
    }
}
