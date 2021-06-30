package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRepository {

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();

    private final AtomicInteger identity = new AtomicInteger(0);

    public List<Product> findAll(){
        return new ArrayList<>(productMap.values());
    }

    public Optional<Product> getById(long _id){
        return Optional.ofNullable(productMap.get(_id));
    }

    public void insert(Product _product) {
        long id = identity.incrementAndGet();
        _product.setId(id);
        productMap.put(id, _product);
    }

    public void update(Product _product) {
        productMap.put(_product.getId(), _product);
    }

    public void remove(long _id){
        productMap.remove(_id);
    }

    @PostConstruct
    public void init(){
        long id = identity.incrementAndGet();
        productMap.put(id, new Product(id, "Pottato", 1.35d));
        id = identity.incrementAndGet();
        productMap.put(id, new Product(id, "Cheese", 5.35d));
        id = identity.incrementAndGet();
        productMap.put(id, new Product(id, "Chicken", 3.35d));
        id = identity.incrementAndGet();
        productMap.put(id, new Product(id, "Bread", 0.35d));
        id = identity.incrementAndGet();
        productMap.put(id, new Product(id, "Beer", 2.35d));
    }
}
