package ru.geekbrainbs.persist;

import ru.geekbrainbs.persist.entity.Buyer;
import ru.geekbrainbs.persist.entity.Product;

import java.util.List;

public class Service {

    private final SessionProvider sp;

    public Service(SessionProvider _sessionProvider) {
        this.sp = _sessionProvider;
    }

    public List<Product> getBuyerProductList(long _buyerId){
        return sp.executeEntityManager(em -> em.createNativeQuery(
                "select p.id\n" +
                   "     , p.title\n" +
                   "     , p.price\n" +
                   "  from orders o\n" +
                   "  join product p\n" +
                   "    on p.id = o.product_id\n" +
                   "where o.buyer_id = :id", Product.class).setParameter("id", _buyerId).getResultList());
    }

    public List<Buyer> getProductBuyers(long _productId){
        return sp.executeEntityManager(em -> em.createNativeQuery(
                "select b.id\n" +
                   "     , b.name\n" +
                   "  from orders o\n" +
                   "  join buyer b\n" +
                   "    on b.id = o.buyer_id\n" +
                   "where o.product_id = :prodcut_id", Buyer.class).setParameter("prodcut_id", _productId).getResultList());
    }

}
