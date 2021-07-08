package ru.geekbrainbs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.geekbrainbs.persist.entity.Buyer;
import ru.geekbrainbs.persist.entity.Product;

import java.util.List;
import java.util.Optional;

public class BuyerDAO  {

    private final SessionProvider sp;

    @Autowired private ApplicationContext ctx;

    public BuyerDAO(SessionProvider _sessionProvider) {
        this.sp = _sessionProvider;
    }

    public Optional<Buyer> getById(long _id){
        return sp.executeEntityManager(em -> Optional.of(em
                .createQuery("select b from Buyer b left join fetch b.productList where b.id = :id", Buyer.class)
                .setParameter("id", _id)
                .getSingleResult()));
    }

    public void addBuyer(Buyer _buyer){
        sp.executeInTtansaction(em -> em.persist(_buyer));
    }

    public void updateBuyer(Buyer _buyer){
        sp.executeInTtansaction(em -> em.createNativeQuery("update buyer set name = :name where id = :id")
                .setParameter("name", _buyer.getName())
                .setParameter("id", _buyer.getId()).executeUpdate());

        if (_buyer.getProductList() != null){
            ProductDAO productDAO = ctx.getBean("productDAO", ProductDAO.class);

            List<Product> temp = _buyer.getProductList();
            for (Product p :temp) {
                productDAO.updateProdcuct(p);
            }
        }
    }

    public void removeBuyer(long _id){
        sp.executeInTtansaction(em -> em.createNativeQuery("delete from buyer where id = :id")
                .setParameter("id", _id).executeUpdate());
    }

}
