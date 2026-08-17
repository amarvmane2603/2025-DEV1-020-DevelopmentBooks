package com.bnppf.development_book.service.pricing.constants;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountRateTest {
    @Test
    void oneBookHasNoDiscount() {
        assertThat(DiscountRate.forGroupSize(1).rate())
                .isEqualByComparingTo("0.00");
    }

    @Test
    void twoBooksHaveFivePercentDiscount() {
        assertThat(DiscountRate.forGroupSize(2).rate())
                .isEqualByComparingTo("0.05");
    }

    @Test
    void threeBooksHaveTenPercentDiscount() {
        assertThat(DiscountRate.forGroupSize(3).rate())
                .isEqualByComparingTo("0.10");
    }

    @Test
    void fourBooksHaveTwentyPercentDiscount() {
        assertThat(DiscountRate.forGroupSize(4).rate())
                .isEqualByComparingTo("0.20");
    }

    @Test
    void fiveBooksHaveTwentyFivePercentDiscount() {
        assertThat(DiscountRate.forGroupSize(5).rate())
                .isEqualByComparingTo("0.25");
    }
}
