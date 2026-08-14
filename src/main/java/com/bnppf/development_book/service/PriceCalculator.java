package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class PriceCalculator {

    public BigDecimal calculatePrice(List<Book> books) {
        BigDecimal total = Book.UNIT_PRICE.multiply(BigDecimal.valueOf(books.size()));
        long distinctBooks = books.stream().distinct().count();

        if (distinctBooks == 2) {
            return total.multiply(new BigDecimal("0.95"));
        } else if (distinctBooks == 3) {
            return total.multiply(new BigDecimal("0.90"));
        }
        return total;
    }
}
