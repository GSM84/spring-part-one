package ru.geekbrains.persist;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private final EntityManagerFactory emFactory;
    private       EntityManager enttManager;

    public ProductDAO() {
        this.emFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public void addProduct(Product _product){
        enttManager = emFactory.createEntityManager();
        enttManager.getTransaction().begin();

        enttManager.persist(_product);

        enttManager.getTransaction().commit();
        enttManager.close();
    }

    public void removeProduct(long _id){
        enttManager = emFactory.createEntityManager();
        enttManager.getTransaction().begin();

        enttManager.remove(enttManager.find(Product.class, _id));

        enttManager.getTransaction().commit();
        enttManager.close();
    }

    public void updateProcut(Product _product){
        enttManager = emFactory.createEntityManager();
        enttManager.getTransaction().begin();

        enttManager.createQuery("update Product set title = :title, price = :price where id = :id")
                .setParameter("title", _product.getTitle())
                .setParameter("price", _product.getPrice())
                .setParameter("id", _product.getId())
                .executeUpdate();

        enttManager.getTransaction().commit();
        enttManager.close();
    }

    public Product getById(long _id){
        enttManager = emFactory.createEntityManager();

        Product temp = enttManager.find(Product.class, _id);

        enttManager.close();

        return temp;
    }

    public List<Product> getAllProducts(){
        enttManager = emFactory.createEntityManager();
        ArrayList<Product> prdList = (ArrayList<Product>) enttManager.createQuery("select p from Product p").getResultList();

        enttManager.close();
        return prdList;
    }
}
