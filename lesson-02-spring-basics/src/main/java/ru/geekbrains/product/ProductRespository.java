package ru.geekbrains.product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class ProductRespository {

    private List<Product> productList = new ArrayList<>();

    private final AtomicInteger identity = new AtomicInteger(0);

    public List<Product> findAll(){
        return productList;
    }

    public Product getById(int _id){
        for (Product p:productList) {
            if (p.getId() == _id)
                return p;
        }
        return null;

    }

    @PostConstruct
    public void init(){
        productList.add(new Product(1, "Pottato", 1.35d));
        productList.add(new Product(2, "Cheese", 5.35d));
        productList.add(new Product(3, "Chicken", 3.35d));
        productList.add(new Product(4, "Bread", 0.35d));
        productList.add(new Product(5, "Beer", 2.35d));
    }
}
