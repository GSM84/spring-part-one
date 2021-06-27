package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository _productRepository) {
        this.productRepository = _productRepository;
    }

    @GetMapping
    public String listPage(Model _model){
        logger.info("Product list page requested.");
        _model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model _model){
        logger.info("New product page requested.");

        _model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping
    public String update(Product _product){
        logger.info(String.format("Update product id-%s, title - %s, price - %s", _product.getId(), _product.getTitle(), _product.getPrice()));

        if (productRepository.getById(_product.getId()) == null) {
            logger.info(String.format("Product with id %s resolved as new. Going to insert.", _product.getId()));

            productRepository.insert(_product);
        } else {
            logger.info(String.format("Product with id %s resolved as existing. Going to update fields.", _product.getId()));

            productRepository.update(_product);
        }
        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") long _id, Model _model){
        logger.info(String.format("Edit product request for id - %s", _id));

        _model.addAttribute("product", productRepository.getById(_id));
        return "product_form";
    }

    @GetMapping("/{id}-remove")
    public String productRemove(@PathVariable("id") long _id){
        logger.info(String.format("Going to remove product with id - %s", _id));

        productRepository.remove(_id);

        return "redirect:/product";
    }
}
