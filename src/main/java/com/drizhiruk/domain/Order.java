package com.drizhiruk.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private long id;
    private Client client;
    private String date;
    List<ProductInOrder> products = new ArrayList<ProductInOrder>();

    public Order(Client client, String date, List<ProductInOrder> products) {
        this.client = client;
        this.date = date;
        this.products = products;
    }

    public Order() {
    }

    public Order(long id, Client client, String date) {
        this.id = id;
        this.client = client;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public String getDate() {
        return date;
    }

    public List<ProductInOrder> getProducts() {
        return products;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProducts(List<ProductInOrder> products) {
        this.products = products;
    }

    public void addToProducts(ProductInOrder product) {
        this.products.add(product);
    }

    public void removeFromProducts(ProductInOrder product) {
        this.products.remove(product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", date='" + date +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }
}
