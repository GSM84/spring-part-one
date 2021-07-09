package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

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
    public String listPage(@RequestParam(value = "minPrice", required = false)BigDecimal _minPrice
                         , @RequestParam(value = "maxPrice", required = false)BigDecimal _maxPrice
                         , Model _model){
        logger.info(String.format("Product list page requested with minPrice - %s, maxPrice - %s", _minPrice, _maxPrice));

        List<Product> resultSet = null;
        if (_minPrice != null && _maxPrice != null){
            resultSet = productRepository.findByPriceBetween(_minPrice, _maxPrice);
        } else if (_minPrice != null){
            resultSet = productRepository.findByPriceGreater(_minPrice);
        } else if (_maxPrice != null) {
            resultSet = productRepository.findByPriceLess(_maxPrice);
        } else {
            resultSet = productRepository.findAll();
        }

        _model.addAttribute("products", resultSet);
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model _model){
        logger.info("New product page requested.");

        _model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping
    public String update(@Valid Product _product, BindingResult result){
        logger.info(String.format("Update product id-%s, title - %s, price - %s", _product.getId(), _product.getTitle(), _product.getPrice()));

        if(result.hasErrors()){
            logger.info(String.format("Some of parameter values are incorrect id-%s, title - %s, price - %s", _product.getId(), _product.getTitle(), _product.getPrice()));

            return "product_form";
        }

        productRepository.save(_product);

        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long _id, Model _model){
        logger.info(String.format("Edit product request for id - %s", _id));

        _model.addAttribute("product", productRepository.findById(_id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id - %s not exists.", _id))));
        return "product_form";
    }

    @GetMapping("/prd")
    public String productRemove(@RequestParam(name="id") Long _id, @RequestParam(name="action") String _action){
        logger.info(String.format("Going to %s product with id - %s", _action, _id));

        if (_action.equals(Actions.REMOVE.toString())) {
            productRepository.deleteById(_id);
        }

        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView NotFoundExceptionHandler(NotFoundException _exception){
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", _exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }

}
