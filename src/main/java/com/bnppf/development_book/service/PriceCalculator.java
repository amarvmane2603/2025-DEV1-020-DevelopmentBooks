package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Basket;

import com.bnppf.development_book.service.pricing.PricingStrategy;
import java.math.BigDecimal;

import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Strategy (GoF) Context: the stable entry point the rest of the application
 * depends on. It holds no pricing logic itself - it just delegates to
 * whichever {@link PricingStrategy} it was configured with, so the algorithm
 * can change (or be swapped for a different one) without touching callers.
 */
@Service
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
