package com.bnppf.development_book.service.pricing.impl;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;
import com.bnppf.development_book.service.pricing.PricingStrategy;
import com.bnppf.development_book.service.pricing.constants.DiscountRate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DiscountPricingStrategy implements PricingStrategy {

    private static final int MONEY_SCALE = 2;

    private final GroupSizeCalculator groupSizeCalculator = new GroupSizeCalculator();
    private final GroupSizeAdjuster groupSizeAdjuster = new GroupSizeAdjuster();

    @Override
    public BigDecimal priceFor(Basket basket) {
        List<Integer> groupSizes = groupSizeCalculator.calculateGroupSizes(basket);
        List<Integer> bestGroupSizes = groupSizeAdjuster.adjustForBestPrice(groupSizes);

        return bestGroupSizes.stream()
                .map(this::priceForGroup)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal priceForGroup(int groupSize) {
        BigDecimal regularPrice = Book.UNIT_PRICE.multiply(BigDecimal.valueOf(groupSize));
        BigDecimal discount = DiscountRate.forGroupSize(groupSize).rate();

        return regularPrice.multiply(BigDecimal.ONE.subtract(discount));
    }
}
