package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.persist.Product;
import ru.geekbrains.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService _productService) {
        this.productService = _productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Product getById(@PathVariable("id") Long _id){
        return productService.findById(_id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id - %s not exists.", _id)));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(produces = "application/json")
    public Product create(@RequestBody Product _product){
        if(_product.getId() != null){
            throw  new BadRequestException("Product id has to be null");
        }
        productService.save(_product);
        return _product;
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(produces = "application/json")
    public void update(@RequestBody Product _product){
        if(_product.getId() == null){
            throw  new BadRequestException("Product id has to be not null!");
        }
        productService.save(_product);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    @Secured("ROLE_ADMIN")
    public void delete(@PathVariable("id") Long _id){
        if(_id == null){
            throw  new BadRequestException("Product id has to be not null!");
        }
        productService.deleteById(_id);
    }
}
