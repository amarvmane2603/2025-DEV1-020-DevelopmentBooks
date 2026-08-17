package com.bnppf.development_book.service.pricing.impl;

import java.util.ArrayList;
import java.util.List;

public class GroupSizeAdjuster {

    private static final int FIVE_BOOKS = 5;
    private static final int THREE_BOOKS = 3;
    private static final int FOUR_BOOKS = 4;

    public List<Integer> adjustForBestPrice(List<Integer> groupSizes) {
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
}