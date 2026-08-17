package com.bnppf.development_book.service.pricing.impl;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupSizeCalculatorTest {
    private final GroupSizeCalculator calculator = new GroupSizeCalculator();

    @Test
    void differentBooksCreateOneGroup() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER,
                Book.CLEAN_ARCHITECTURE);

        assertThat(calculator.calculateGroupSizes(basket))
                .containsExactly(3);
    }

    @Test
    void duplicateBookCreatesSecondGroup() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE,
                Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER);

        assertThat(calculator.calculateGroupSizes(basket))
                .containsExactly(2, 1);
    }
}
