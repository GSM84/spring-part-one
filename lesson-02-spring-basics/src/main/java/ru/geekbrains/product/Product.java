package ru.geekbrains.product;

public class Product {

    private int id;

    private String title;

    private double price;

    public Product(int _id, String _title, double _price) {
        this.id = _id;
        this.title = _title;
        this.price = _price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
