package com.bnppf.assignment.development_book.service;

import com.bnppf.assignment.development_book.model.Book;
import java.math.BigDecimal;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {

    @Test
    void oneBookCostsFifty() {
        List<Book> books = List.of(Book.CLEAN_CODE);

        BigDecimal price = priceCalculator.calculatePrice(books);

        assertThat(price).isEqualByComparingTo("50.00");
    }
}
