package ru.geekbrains.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();

    private final AtomicLong identity = new AtomicLong(0);

    public List<Product> findAll(){
        return new ArrayList<>(productMap.values());
    }

    public Product getProductById(Long _id){
        if (productMap.containsKey(_id))
            return productMap.get(_id);
        else
            return null;
    }

    public void save(Product _product){
        if(_product.getId() == null){
            long id = identity.getAndIncrement();
            _product.setId(id);
        }

        productMap.put(_product.getId(), _product);
    }

    public void delete(long _id){
        productMap.remove(_id);
    }
}
