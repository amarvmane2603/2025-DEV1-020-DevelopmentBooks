package com.bnppf.development_book.model;

import java.math.BigDecimal;

/**
 * The five development books sold as part of the promotional set.
 * Every book costs the same; a future discount will depend only on how many
 * different books are bought together.
 */
public enum Book {
    CLEAN_CODE,
    THE_CLEAN_CODER,
    CLEAN_ARCHITECTURE,
    TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE,
    WORKING_EFFECTIVELY_WITH_LEGACY_CODE;

    /** The one price every book is sold at. */
    public static final BigDecimal UNIT_PRICE = new BigDecimal("50.00");
}

