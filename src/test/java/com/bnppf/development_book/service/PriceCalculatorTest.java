package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;
import com.bnppf.development_book.service.pricing.PricingStrategy;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {

    @Test
    void usesPricingStrategyToCalculatePrice() {
        PricingStrategy pricingStrategy = basket -> new BigDecimal("50.00");
        PriceCalculator calculator = new PriceCalculator(pricingStrategy);

        BigDecimal price = calculator.priceFor(Basket.of(Book.CLEAN_CODE));

        assertThat(price).isEqualByComparingTo("50.00");
    }
}
