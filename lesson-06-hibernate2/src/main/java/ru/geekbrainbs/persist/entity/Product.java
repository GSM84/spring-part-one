package ru.geekbrainbs.persist.entity;

import javax.persistence.*;

@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String title;

    @Column(nullable = false)
    private double price;

    @JoinColumn(name = "buyer_id")
    @ManyToOne
    private Buyer buyer;

    public Product() {
    }

    public Product(Long id, String title, double price, Buyer buyer) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.buyer  = buyer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return String.format("Product{id:%d;title:'%s';price:%f;}", this.id, this.title, this.price);
    }
}
