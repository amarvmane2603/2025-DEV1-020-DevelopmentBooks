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

    public BigDecimal calculatePrice(Basket basket) {
        List<Integer> groupSizes = groupSizes(basket);

        BigDecimal total = BigDecimal.ZERO;
        for (int groupSize : groupSizes) {
            total = total.add(priceForGroup(groupSize));
        }
        return total;
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
