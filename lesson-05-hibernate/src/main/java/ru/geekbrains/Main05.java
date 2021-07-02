package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductDAO;

public class Main05 {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();

        Product prd = dao.getById(3);

        prd.setPrice(18.3d);

        dao.updateProcut(prd);

        for (Product p:dao.getAllProducts()) {
            System.out.println(p);
        }

    }
}
