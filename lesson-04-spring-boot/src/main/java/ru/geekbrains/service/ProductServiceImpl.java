package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.ProductListParams;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findWithFilter(ProductListParams _params) {
        Specification<Product> spec = Specification.where(null);
        if(_params.getMinPrice() != null){
            spec = spec.and(ProductSpecification.minPrice(_params.getMinPrice()));
        }
        if(_params.getMaxPrice() != null){
            spec = spec.and(ProductSpecification.maxPrice(_params.getMaxPrice()));
        }
        if(_params.getSortType() != null && !_params.getSortType().isBlank() && _params.getSortField() != null && !_params.getSortField().isBlank()) {

            return productRepository.findAll(spec, PageRequest.of(
                    Optional.ofNullable(_params.getPageNum()).orElse(1) - 1
                    , Optional.ofNullable(_params.getPageSize()).orElse(7)
                    , Sort.by(Sort.Direction.fromString(_params.getSortType()), _params.getSortField())));
        } else {
           return productRepository.findAll(spec, PageRequest.of(
                    Optional.ofNullable(_params.getPageNum()).orElse(1) - 1
                    , Optional.ofNullable(_params.getPageSize()).orElse(7)));
        }

    }

    @Override
    public Optional<Product> findById(Long _id) {
        return productRepository.findById(_id);
    }

    @Override
    public void deleteById(Long _id) {
        productRepository.deleteById(_id);
    }

    @Override
    public void save(Product _product) {
        productRepository.save(_product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

}
