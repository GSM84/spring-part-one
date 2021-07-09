package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from product where price > ?1", nativeQuery = true)
    List<Product> findByPriceGreater(BigDecimal _minPrice);

    @Query(value = "select * from product where price < ?1", nativeQuery = true)
    List<Product> findByPriceLess(BigDecimal _maxPrice);

    @Query(value = "select * from product where price between ?1 and ?2", nativeQuery = true)
    List<Product> findByPriceBetween(BigDecimal _minPrice, BigDecimal _maxPrice);
}
