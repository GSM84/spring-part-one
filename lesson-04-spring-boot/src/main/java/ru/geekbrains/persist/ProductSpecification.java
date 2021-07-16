package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class ProductSpecification {

    public static Specification<Product> minPrice(BigDecimal _minPrice){
        return (root, query, builder) -> builder.ge(root.get("price"), _minPrice);
    }

    public static Specification<Product> maxPrice(BigDecimal _maxPrice){
        return (root, query, builder) -> builder.le(root.get("price"), _maxPrice);
    }
}
