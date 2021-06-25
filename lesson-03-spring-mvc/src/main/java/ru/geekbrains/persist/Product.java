package ru.geekbrains.persist;

public class Product {
    private long id;

    private String title;

    private double price;

    public Product(long _id, String _title, double _price) {
        this.id = _id;
        this.title = _title;
        this.price = _price;
    }

    public Product() {
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
}
