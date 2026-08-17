package com.bnppf.development_book.service.pricing;

import com.bnppf.development_book.model.Basket;
import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal priceFor(Basket basket);
}
