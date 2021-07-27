package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService _prodcutService) {
        this.productService = _prodcutService;
    }

    @GetMapping()
    public String listPage(ProductListParams _paramsDTO
                         , Model _model){
        logger.info(String.format("Product list page requested with minPrice - %s, maxPrice - %s, sort type %s"
                , _paramsDTO.getMinPrice(), _paramsDTO.getMaxPrice(), _paramsDTO.getSortType()));


        if(_paramsDTO.getSortType() != null && !_paramsDTO.getSortType().isBlank()
                && _paramsDTO.getSortField() != null && !_paramsDTO.getSortField().isBlank()) {
            _model.addAttribute("sortType", _paramsDTO.getSortType().equals("asc") ? "desc" : "asc");
        } else {
            _model.addAttribute("sortType", "asc");
        }

        _model.addAttribute("products", productService.findWithFilter(_paramsDTO));

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

        productService.save(_product);

        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long _id, Model _model){
        logger.info(String.format("Edit product request for id - %s", _id));

        _model.addAttribute("product", productService.findById(_id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id - %s not exists.", _id))));
        return "product_form";
    }

    @GetMapping("/prd")
    public String productRemove(@RequestParam(name="id") Long _id, @RequestParam(name="action") String _action){
        logger.info(String.format("Going to %s product with id - %s", _action, _id));

        if (_action.equals(Actions.REMOVE.toString())) {
            productService.deleteById(_id);
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
