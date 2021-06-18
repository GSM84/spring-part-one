package ru.geekbrains.persist;

public class Product {

    private long id;

    private String titel;

    private double price;

    public Product(long id, String titel, double price) {
        this.id = id;
        this.titel = titel;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
