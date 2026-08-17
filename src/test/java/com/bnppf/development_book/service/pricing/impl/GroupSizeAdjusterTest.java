package com.bnppf.development_book.service.pricing.impl;

import java.util.List;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class GroupSizeAdjusterTest {
    private final GroupSizeAdjuster adjuster = new GroupSizeAdjuster();

    @Test
    void changesFiveAndThreeIntoFourAndFour() {
        List<Integer> adjusted = adjuster.adjustForBestPrice(List.of(5, 3));

        assertThat(adjusted).containsExactlyInAnyOrder(4, 4);
    }

    @Test
    void keepsGroupsThatDoNotNeedAdjustment() {
        List<Integer> adjusted = adjuster.adjustForBestPrice(List.of(5, 2));

        assertThat(adjusted).containsExactlyInAnyOrder(5, 2);
    }
}
