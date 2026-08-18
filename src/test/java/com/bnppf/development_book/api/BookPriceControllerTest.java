package com.bnppf.development_book.api;

import com.bnppf.development_book.exception.GlobalExceptionHandler;
import com.bnppf.development_book.service.BookCatalog;
import com.bnppf.development_book.service.PriceCalculator;
import com.bnppf.development_book.service.pricing.impl.DiscountPricingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookPriceController.class)
@Import({DiscountPricingStrategy.class, PriceCalculator.class, BookCatalog.class, GlobalExceptionHandler.class})
class BookPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void pricesABasketOfBooks() throws Exception {
        // Arrange
        String requestBody = """
                {"books": ["CLEAN_CODE", "THE_CLEAN_CODER"]}
                """;

        // Act
        var response = mockMvc.perform(post("/api/books/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(95.00))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void pricesAnEmptyBasketAsZero() throws Exception {
        // Arrange
        String requestBody = """
                {"books": []}
                """;

        // Act
        var response = mockMvc.perform(post("/api/books/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(0.00));
    }

    @Test
    void returnsBadRequestForAnUnknownBook() throws Exception {
        // Arrange
        String requestBody = """
                {"books": ["NOT_A_REAL_BOOK"]}
                """;

        // Act
        var response = mockMvc.perform(post("/api/books/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // Assert
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Unknown book: NOT_A_REAL_BOOK"));
    }

    @Test
    void returnsBadRequestWhenBooksFieldIsMissing() throws Exception {
        // Arrange
        String requestBody = "{}";

        // Act
        var response = mockMvc.perform(post("/api/books/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // Assert
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("books must be provided"));
    }
}
