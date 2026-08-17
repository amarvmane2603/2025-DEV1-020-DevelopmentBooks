package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Basket;

import com.bnppf.development_book.service.pricing.PricingStrategy;
import java.math.BigDecimal;

import java.util.Objects;

/**
 * Calculates the price of a basket of development books, applying a
 * discount when several different titles are bought together and finding
 * the cheapest way to group any duplicate copies.
 */
public class PriceCalculator {

    private final PricingStrategy pricingStrategy;

    public PriceCalculator(PricingStrategy pricingStrategy) {
        this.pricingStrategy = Objects.requireNonNull(pricingStrategy, "pricingStrategy must not be null");
    }

    public BigDecimal priceFor(Basket basket) {
        Objects.requireNonNull(basket, "basket must not be null");
        return pricingStrategy.priceFor(basket);
    }
}
