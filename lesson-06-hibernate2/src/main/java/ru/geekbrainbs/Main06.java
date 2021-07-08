package ru.geekbrainbs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrainbs.persist.*;
import ru.geekbrainbs.persist.entity.Buyer;
import ru.geekbrainbs.persist.entity.Product;

public class Main06 {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        BuyerDAO buyerDAO = context.getBean("buyerDAO", BuyerDAO.class);

        ProductDAO productDAO = context.getBean("productDAO", ProductDAO.class);

        Service srv = context.getBean("service", Service.class);

//        List buyerList = srv.getProductBuyers(1l);
//
//        for (Object p: buyerList){
//            System.out.println(p);
//        }

//        Buyer nBuyer = new Buyer(null, "Vasya");
//        nBuyer.getProductList().add(new Product(null, "Meal", 23.2, nBuyer));
//        nBuyer.getProductList().add(new Product(null, "Meal2", 2.95, nBuyer));
//
//
//        buyerDAO.addBuyer(nBuyer);

        Buyer nBuyer = buyerDAO.getById(6).get();

        Product newProd = new Product(null, "Any product", 99.99, nBuyer);

        productDAO.addProductr(newProd);

    }
}
