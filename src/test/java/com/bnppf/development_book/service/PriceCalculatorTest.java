package com.bnppf.development_book.service;

import com.bnppf.development_book.model.Basket;
import com.bnppf.development_book.model.Book;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {

    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    void emptyBasketCostsZero() {
        BigDecimal price = priceCalculator.calculatePrice(Basket.of());

        assertThat(price).isEqualByComparingTo("0.00");
    }

    @Test
    void oneBookCostsFifty() {
        BigDecimal price = priceCalculator.calculatePrice(Basket.of(Book.CLEAN_CODE));

        assertThat(price).isEqualByComparingTo("50.00");
    }

    @Test
    void twoDifferentBooksGetFivePercentDiscount() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("95.00");
    }

    @Test
    void threeDifferentBooksGetTenPercentDiscount() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER,
                Book.CLEAN_ARCHITECTURE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("135.00");
    }

    @Test
    void fourDifferentBooksGetTwentyPercentDiscount() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER,
                Book.CLEAN_ARCHITECTURE,
                Book.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("160.00");
    }

    @Test
    void fiveDifferentBooksGetTwentyFivePercentDiscount() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER,
                Book.CLEAN_ARCHITECTURE,
                Book.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE,
                Book.WORKING_EFFECTIVELY_WITH_LEGACY_CODE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("187.50");
    }

    @Test
    void duplicateBooksDoNotGetAGroupDiscount() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE,
                Book.CLEAN_CODE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("100.00");
    }

    @Test
    void threeCopiesOfSameBookCostFullPrice() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE,
                Book.CLEAN_CODE,
                Book.CLEAN_CODE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("150.00");
    }

    @Test
    void twoPairsCreateTwoDiscountedGroups() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE, Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER, Book.THE_CLEAN_CODER);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("190.00");
    }

    @Test
    void fourDifferentBooksAndOneDuplicateCreateGroupsOfFourAndOne() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE, Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER,
                Book.CLEAN_ARCHITECTURE,
                Book.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("210.00");
    }

    @Test
    void fourDifferentBooksWithTwoDuplicatesCreateGroupsOfFourAndTwo() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE, Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER, Book.THE_CLEAN_CODER,
                Book.CLEAN_ARCHITECTURE,
                Book.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("255.00");
    }

    @Test
    void fiveDifferentBooksAndTwoDuplicatesCreateGroupsOfFiveAndTwo() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE, Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER, Book.THE_CLEAN_CODER,
                Book.CLEAN_ARCHITECTURE,
                Book.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE,
                Book.WORKING_EFFECTIVELY_WITH_LEGACY_CODE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("282.50");
    }

    @Test
    void onePairAndTwoSingleCopiesArePricedAsSeparateGroups() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE, Book.CLEAN_CODE, Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("195.00");
    }

    @Test
    void twoCompleteSetsOfFiveGetDiscountForBothGroups() {
        Basket basket = Basket.of(
                Book.CLEAN_CODE, Book.CLEAN_CODE,
                Book.THE_CLEAN_CODER, Book.THE_CLEAN_CODER,
                Book.CLEAN_ARCHITECTURE, Book.CLEAN_ARCHITECTURE,
                Book.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE, Book.TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE,
                Book.WORKING_EFFECTIVELY_WITH_LEGACY_CODE, Book.WORKING_EFFECTIVELY_WITH_LEGACY_CODE);

        BigDecimal price = priceCalculator.calculatePrice(basket);

        assertThat(price).isEqualByComparingTo("375.00");
    }

}
