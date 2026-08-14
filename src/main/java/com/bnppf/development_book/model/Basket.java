package com.bnppf.development_book.model;

import java.util.List;
import java.util.Objects;

public final class Basket {

    private static final Basket EMPTY = new Basket(List.of());
    private final List<Book> items;

    private Basket(List<Book> items) {
        this.items = items;
    }

    public static Basket of(List<Book> books) {
        validate(books);
        return new Basket(List.copyOf(books));
    }

    public static Basket of(Book... books) {
        return of(List.of(books));
    }

    private static void validate(List<Book> books) {
        Objects.requireNonNull(books, "books must not be null");
        if (books.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("books must not contain null entries");
        }
    }
    public List<Book> items() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }
}
