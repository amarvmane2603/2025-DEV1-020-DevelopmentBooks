package com.bnppf.assignment.development_book.service;

import com.bnppf.assignment.development_book.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class PriceCalculator {

    public BigDecimal calculatePrice(List<Book> books) {
        int copies = books.size();
        return Book.UNIT_PRICE.multiply(BigDecimal.valueOf(copies));
    }
}
