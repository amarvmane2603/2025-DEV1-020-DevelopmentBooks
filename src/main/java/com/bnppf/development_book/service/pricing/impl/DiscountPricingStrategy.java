package com.bnppf.development_book.service.pricing.impl;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;
import com.bnppf.development_book.service.pricing.PricingStrategy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DiscountPricingStrategy implements PricingStrategy {

    private static final int MONEY_SCALE = 2;
    private static final BigDecimal[] DISCOUNT_RATES = {
            new BigDecimal("0.00"),
            new BigDecimal("0.05"),
            new BigDecimal("0.10"),
            new BigDecimal("0.20"),
            new BigDecimal("0.25")
    };
    private static final int FIVE_BOOKS = 5;
    private static final int THREE_BOOKS = 3;
    private static final int FOUR_BOOKS = 4;

    private final GroupSizeCalculator groupSizeCalculator = new GroupSizeCalculator();

    @Override
    public BigDecimal priceFor(Basket basket) {
        List<Integer> groupSizes = groupSizeCalculator.calculateGroupSizes(basket);
        List<Integer> bestGroupSizes = adjustForBestPrice(groupSizes);

        return bestGroupSizes.stream()
                .map(this::priceForGroup)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    private List<Integer> adjustForBestPrice(List<Integer> groupSizes) {
        List<Integer> adjustedGroups = new ArrayList<>(groupSizes);

        while (adjustedGroups.contains(FIVE_BOOKS) && adjustedGroups.contains(THREE_BOOKS)) {
            replaceOne(adjustedGroups, FIVE_BOOKS, FOUR_BOOKS);
            replaceOne(adjustedGroups, THREE_BOOKS, FOUR_BOOKS);
        }

        return adjustedGroups;
    }

    private void replaceOne(List<Integer> groups, int currentSize, int newSize) {
        groups.set(groups.indexOf(currentSize), newSize);
    }

    private BigDecimal priceForGroup(int groupSize) {
        BigDecimal regularPrice = Book.UNIT_PRICE.multiply(BigDecimal.valueOf(groupSize));
        BigDecimal discountRate = DISCOUNT_RATES[groupSize - 1];

        return regularPrice.multiply(BigDecimal.ONE.subtract(discountRate));
    }
}
