package ru.geekbrainbs.persist.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="buyer")
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String name;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Product> productList = new ArrayList<>();

    public Buyer() {
    }

    public Buyer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return String.format("Buyer{id:%d;name:'%s';}", this.id, this.name);
    }
}
