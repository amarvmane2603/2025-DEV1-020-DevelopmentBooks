package com.bnppf.development_book.api;


import com.bnppf.development_book.dto.PriceRequest;
import com.bnppf.development_book.dto.PriceResponse;
import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;
import com.bnppf.development_book.service.BookCatalog;
import com.bnppf.development_book.service.PriceCalculator;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookPriceController {

    private final PriceCalculator priceCalculator;
    private final BookCatalog bookCatalog;

    public BookPriceController(PriceCalculator priceCalculator, BookCatalog bookCatalog) {
        this.priceCalculator = priceCalculator;
        this.bookCatalog = bookCatalog;
    }

    @PostMapping("/price")
    public PriceResponse calculatePrice(@Valid @RequestBody PriceRequest request) {
        Basket basket = Basket.of(toBooks(request.books()));

        return PriceResponse.of(priceCalculator.priceFor(basket));
    }

    private List<Book> toBooks(List<String> names) {
        return names.stream().map(bookCatalog::findByName).toList();
    }
}
