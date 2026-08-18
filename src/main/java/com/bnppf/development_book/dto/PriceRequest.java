package com.bnppf.development_book.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PriceRequest(

        @NotNull List<String> books) {
}
