package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.product.ProductRespository;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductRespository repos = context.getBean("productRespository", ProductRespository.class);
        Cart cart = context.getBean("cart", Cart.class);
        Cart cart2 = context.getBean("cart", Cart.class);

        cart.addToCart(repos.getById(1).getId());
        cart.addToCart(repos.getById(2).getId());
        cart.addToCart(repos.getById(1).getId());
        cart.addToCart(repos.getById(3).getId());
        cart.addToCart(repos.getById(1).getId());

        cart.printCart();
        cart.removeFromCard(1);
        cart.removeFromCard(2);
        System.out.println("------------------");
        cart.printCart();

    }
}
