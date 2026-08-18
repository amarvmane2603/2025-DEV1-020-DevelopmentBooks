# Development Books Kata

A Java 21 + Spring Boot microservice that prices a basket of development
books, applying a discount when several *different* titles are bought
together.

## Business rules

Each book costs **EUR 50.00**.

| Different books in a group | Discount |
|---:|---:|
| 1 | 0% |
| 2 | 5% |
| 3 | 10% |
| 4 | 20% |
| 5 | 25% |

Copies of the same book never receive a discount by themselves - the
discount only applies across *different* books bought together. When a
basket mixes duplicate copies with several distinct titles, it is split
into the cheapest possible grouping. The one special case the kata calls
out explicitly: a group of 5 and a group of 3 is billed as two groups of 4
instead, since `4 + 4` (`320.00`) is cheaper than `5 + 3` (`322.50`).

## Requirements

- Java 21
- Maven 3.9+

## Build

```bash
mvn clean package
```

## Test

```bash
mvn clean test
```

## Run

```bash
mvn spring-boot:run
```

The service starts on `:8080`.

```http
POST /api/books/price
Content-Type: application/json

{"books": ["CLEAN_CODE", "CLEAN_CODE", "THE_CLEAN_CODER"]}
```

```json
{ "price": 145.00, "currency": "EUR" }
```

An unknown book or a missing `books` field returns `400 Bad Request` with
`{ "message": "..." }`.


## Testing strategy


| Class | Tests | What it proves |
|---|---:|---|
| `BasketTest` | 4 | construction, null-safety, immutability |
| `DiscountRateTest` | 5 | the five official discount rates |
| `GroupSizeCalculatorTest` | 2 | one-copy-per-title grouping, duplicates spilling into a second group |
| `GroupSizeAdjusterTest` | 2 | the 5-plus-3-becomes-4-plus-4 correction, and a case that needs no adjustment |
| `DiscountPricingStrategyTest` | 15 | empty basket, all five group-size discounts, duplicate-copy grouping, the official acceptance case |
| `PriceCalculatorTest` | 1 | the Strategy context delegates to whatever `PricingStrategy` it is given |
| `BookCatalogTest` | 2 | resolves a catalogue book, rejects an unknown name |
| `BookPriceControllerTest` | 4 | the HTTP contract: happy path, empty basket, unknown book, missing field |
| **Total** | **35** | |

