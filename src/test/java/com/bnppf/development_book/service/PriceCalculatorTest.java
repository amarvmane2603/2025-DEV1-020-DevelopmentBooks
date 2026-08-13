package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Book;
import java.math.BigDecimal;

import java.util.List;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {

    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    void oneBookCostsFifty() {

        List<Book> books = List.of(Book.CLEAN_CODE);
        BigDecimal price = priceCalculator.calculatePrice(books);
        assertThat(price).isEqualByComparingTo("50.00");
    }

    @Test
    void duplicateBooksDoNotGetAGroupDiscount() {

        List<Book> books = List.of(Book.CLEAN_CODE, Book.CLEAN_CODE);
        BigDecimal price = priceCalculator.calculatePrice(books);
        assertThat(price).isEqualByComparingTo("100.00");
    }
}
