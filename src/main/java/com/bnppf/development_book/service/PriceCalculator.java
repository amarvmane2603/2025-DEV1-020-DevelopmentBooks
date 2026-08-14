package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class PriceCalculator {

    private static final BigDecimal[] DISCOUNT_RATES = {
            new BigDecimal("0.00"),
            new BigDecimal("0.05"),
            new BigDecimal("0.10"),
            new BigDecimal("0.20"),
            new BigDecimal("0.25"),

    };

    public BigDecimal calculatePrice(Basket basket) {
        if (basket.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = Book.UNIT_PRICE.multiply(BigDecimal.valueOf(basket.size()));
        int distinctBooks = (int) basket.items().stream().distinct().count();
        BigDecimal discountRate = DISCOUNT_RATES[distinctBooks - 1];

        return total.multiply(BigDecimal.ONE.subtract(discountRate));
    }
}
