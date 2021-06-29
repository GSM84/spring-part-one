package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

        if (_product.getId() == 0) {
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

        _model.addAttribute("product", productRepository.getById(_id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id - %s not exists.", _id))));
        return "product_form";
    }

    @GetMapping("/prd")
    public String productRemove(@RequestParam(name="id") long _id, @RequestParam(name="action") String _action){
        logger.info(String.format("Going to %s product with id - %s", _action, _id));

        if (_action.equals(Actions.REMOVE.toString())) {
            productRepository.remove(_id);
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
