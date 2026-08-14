package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Calculates the price of a basket of development books, applying a
 * discount when several different titles are bought together and finding
 * the cheapest way to group any duplicate copies.
 */
public class PriceCalculator {

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

    public BigDecimal calculatePrice(Basket basket) {
        List<Integer> groupSizes = groupSizes(basket);
        List<Integer> bestGroupSizes = adjustForBestPrice(groupSizes);

        return bestGroupSizes.stream()
                .map(this::priceForGroup)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Repeatedly takes one copy of every title still left in the basket to
     * form a group. Example: CLEAN_CODE, CLEAN_CODE, THE_CLEAN_CODER
     * becomes groups of 2 and 1.
     */
    private List<Integer> groupSizes(Basket basket) {
        Map<Book, Integer> copiesByBook = countCopies(basket);
        List<Integer> groupSizes = new ArrayList<>();

        while (hasBooksLeft(copiesByBook)) {
            groupSizes.add(takeOneOfEachBook(copiesByBook));
        }

        return groupSizes;
    }

    private Map<Book, Integer> countCopies(Basket basket) {
        Map<Book, Integer> copiesByBook = new EnumMap<>(Book.class);

        for (Book book : basket.items()) {
            copiesByBook.merge(book, 1, Integer::sum);
        }

        return copiesByBook;
    }

    private boolean hasBooksLeft(Map<Book, Integer> copiesByBook) {
        return copiesByBook.values().stream().anyMatch(copies -> copies > 0);
    }

    private int takeOneOfEachBook(Map<Book, Integer> copiesByBook) {
        int groupSize = 0;

        for (Map.Entry<Book, Integer> entry : copiesByBook.entrySet()) {
            if (entry.getValue() > 0) {
                entry.setValue(entry.getValue() - 1);
                groupSize++;
            }
        }

        return groupSize;
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
