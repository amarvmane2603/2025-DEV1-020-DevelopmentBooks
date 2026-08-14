package com.bnppf.development_book.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BasketTest {

    @Test
    void emptyBasketHasNoBooks() {
        Basket basket = Basket.empty();

        assertThat(basket.items()).isEmpty();
    }

    @Test
    void basketContainsAddedBooks() {
        Basket basket = Basket.of(Book.CLEAN_CODE, Book.THE_CLEAN_CODER);

        assertThat(basket.items())
                .containsExactly(Book.CLEAN_CODE, Book.THE_CLEAN_CODER);
    }
    @Test
    void basketRejectsNullList() {
        assertThatThrownBy(() -> Basket.of((List<Book>) null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void basketRejectsNullBook() {
        assertThatThrownBy(() -> Basket.of(Book.CLEAN_CODE, null))
                .isInstanceOf(NullPointerException.class);
    }
}
