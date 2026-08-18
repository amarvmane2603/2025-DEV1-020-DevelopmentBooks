package com.bnppf.development_book.dto;

import java.math.BigDecimal;

public record PriceResponse(BigDecimal price, String currency) {

    public static PriceResponse of(BigDecimal price) {
        return new PriceResponse(price, "EUR");
    }
}
