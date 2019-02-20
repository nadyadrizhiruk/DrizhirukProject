package com.drizhiruk.domain;

import java.util.List;

public class Order {

    private long id;
    private Client client;
    private String date;
    List<Product> products;

    public Order(Client client, String date, List<Product> products) {
        this.client = client;
        this.date = date;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
