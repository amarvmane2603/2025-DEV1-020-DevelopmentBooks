package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PriceCalculator {

    private static final BigDecimal[] DISCOUNT_RATES = {
            new BigDecimal("0.00"),
            new BigDecimal("0.05"),
            new BigDecimal("0.10"),
            new BigDecimal("0.20"),
            new BigDecimal("0.25"),

    };

    private static final int FIVE_BOOKS = 5;
    private static final int THREE_BOOKS = 3;
    private static final int FOUR_BOOKS = 4;

    public BigDecimal calculatePrice(Basket basket) {
        List<Integer> groupSizes = groupSizes(basket);
        List<Integer> bestGroupSizes = adjustForBestPrice(groupSizes);

        BigDecimal total = BigDecimal.ZERO;
        for (int groupSize : bestGroupSizes) {
            total = total.add(priceForGroup(groupSize));
        }
        return total;
    }

    private List<Integer> adjustForBestPrice(List<Integer> groupSizes) {
        List<Integer> adjustedGroups = new ArrayList<>(groupSizes);
        while (adjustedGroups.contains(FIVE_BOOKS) && adjustedGroups.contains(THREE_BOOKS)) {
            adjustedGroups.set(adjustedGroups.indexOf(FIVE_BOOKS), FOUR_BOOKS);
            adjustedGroups.set(adjustedGroups.indexOf(THREE_BOOKS), FOUR_BOOKS);
        }
        return adjustedGroups;
    }

    private List<Integer> groupSizes(Basket basket) {
        Map<Book, Integer> copiesByBook = new EnumMap<>(Book.class);
        for (Book book : basket.items()) {
            copiesByBook.merge(book, 1, Integer::sum);
        }

        List<Integer> groupSizes = new ArrayList<>();
        while (copiesByBook.values().stream().anyMatch(c -> c > 0)) {
            int groupSize = 0;
            for (Map.Entry<Book, Integer> entry : copiesByBook.entrySet()) {
                if (entry.getValue() > 0) {
                    entry.setValue(entry.getValue() - 1);
                    groupSize++;
                }
            }
            groupSizes.add(groupSize);
        }
        return groupSizes;
    }

    private BigDecimal priceForGroup(int groupSize) {
        BigDecimal regularPrice = Book.UNIT_PRICE.multiply(BigDecimal.valueOf(groupSize));
        BigDecimal discountRate = DISCOUNT_RATES[groupSize - 1];
        return regularPrice.multiply(BigDecimal.ONE.subtract(discountRate));
    }
}
