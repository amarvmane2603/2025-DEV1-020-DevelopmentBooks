package com.bnppf.development_book.service.pricing.constants;

import java.math.BigDecimal;
import java.util.Arrays;

public enum DiscountRate {

    SINGLE(1, "0.00"),
    PAIR(2, "0.05"),
    TRIO(3, "0.10"),
    QUARTET(4, "0.20"),
    QUINTET(5, "0.25");

    private final int groupSize;
    private final BigDecimal rate;

    DiscountRate(int groupSize, String rate) {
        this.groupSize = groupSize;
        this.rate = new BigDecimal(rate);
    }

    public static DiscountRate forGroupSize(int groupSize) {
        return Arrays.stream(values())
                .filter(entry -> entry.groupSize == groupSize)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No discount defined for a group of " + groupSize + " different books"));
    }

    public BigDecimal rate() {
        return rate;
    }
}
