package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        ProductRepository productRepository = new ProductRepository();

        productRepository.save(new Product(1l, "Prod1", 1.2d));
        productRepository.save(new Product(2l, "Prod2", 1.2d));
        productRepository.save(new Product(3l, "Prod3", 1.2d));
        productRepository.save(new Product(4l, "Prod4", 1.2d));
        productRepository.save(new Product(5l, "Prod5", 1.2d));
        productRepository.save(new Product(6l, "Prod6", 1.2d));
        productRepository.save(new Product(7l, "Prod7", 1.2d));
        productRepository.save(new Product(8l, "Prod8", 1.2d));
        productRepository.save(new Product(9l, "Prod9", 1.2d));
        productRepository.save(new Product(10l, "Prod10", 1.2d));

        sc.setAttribute("productRepository", productRepository);
    }
}
