package com.bnppf.development_book.service.pricing.impl;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GroupSizeCalculator {
    public List<Integer> calculateGroupSizes(Basket basket) {
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
}
