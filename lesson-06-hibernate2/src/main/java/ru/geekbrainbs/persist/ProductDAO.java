package ru.geekbrainbs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.geekbrainbs.persist.entity.Product;

import java.util.Optional;

public class ProductDAO  {

    private final SessionProvider sp;
    @Autowired
    private ApplicationContext ctx;

    public ProductDAO(SessionProvider _sessionProvider) {
        this.sp = _sessionProvider;
    }

    public Optional<Product> getById(long _id){
        return sp.executeEntityManager(em -> Optional.of(em.find(Product.class, _id)));
    }

    public void addProductr(Product _product){
        sp.executeInTtansaction(em -> em.persist(_product));
    }

    public void updateProdcuct(Product _product){
        sp.executeInTtansaction(em -> em.createNativeQuery("update product set title = :title, price = :price where id = :id")
                .setParameter("title", _product.getTitle())
                .setParameter("price", _product.getPrice())
                .setParameter("id", _product.getId()).executeUpdate());
    }

    public void removeProduct(long _id){
        sp.executeInTtansaction(em -> em.createNativeQuery("delete from product where id = :id")
                .setParameter("id", _id).executeUpdate());
    }

}
