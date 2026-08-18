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

Each piece of the pricing logic has its own small tests, plus a few tests
at the end that check the whole API works as expected. If a test fails,
the class name tells you exactly where to look.

| Class | Tests | What it checks |
|---|---:|---|
| `BasketTest` | 4 | A basket of books is created correctly, and rejects bad input like `null` |
| `DiscountRateTest` | 5 | Each of the 5 discount levels (for 1, 2, 3, 4, or 5 different books together) is correct |
| `GroupSizeCalculatorTest` | 2 | Books get grouped correctly — one of each title per group, extra copies start a new group |
| `GroupSizeAdjusterTest` | 2 | The special case where splitting groups a smarter way gives a cheaper price, plus a case where no adjustment is needed |
| `DiscountPricingStrategyTest` | 15 | The actual price calculation — covers an empty basket, all 5 discount levels, duplicate copies, and the official example from the assignment |
| `PriceCalculatorTest` | 1 | The calculator correctly hands off the work to the pricing logic |
| `BookCatalogTest` | 2 | Looking up a real book works, and looking up a fake book name is rejected |
| `BookPriceControllerTest` | 4 | The API itself — a normal request, an empty basket, an unknown book, and a missing field all return the right response |
| **Total** | **35** | |
