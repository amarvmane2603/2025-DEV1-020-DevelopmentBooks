package com.bnppf.development_book.service;

import com.bnppf.development_book.exception.UnknownBookException;
import com.bnppf.development_book.model.Book;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BookCatalogTest {
    private final BookCatalog bookCatalog = new BookCatalog();

    @Test
    void findsBookByName() {
        Book book = bookCatalog.findByName("CLEAN_CODE");

        assertThat(book).isEqualTo(Book.CLEAN_CODE);
    }

    @Test
    void rejectsUnknownBook() {
        assertThatThrownBy(() -> bookCatalog.findByName("UNKNOWN_BOOK"))
                .isInstanceOf(UnknownBookException.class);
    }
}
