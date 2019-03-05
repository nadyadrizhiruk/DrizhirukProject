package com.drizhiruk.domain;

import java.math.BigDecimal;

public class ProductInOrder {
    private long id;

    private Product product;
    private BigDecimal price;
    private int amount;
    private Order order;

    public ProductInOrder(Product product, BigDecimal price, int amount, Order order) {
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.order = order;
    }

    public ProductInOrder(long id, Product product, BigDecimal price, int amount, Order order) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.order = order;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + product.getName() + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    public void setAmount(int amount) {

        this.amount = amount;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public int getAmount() {

        return amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
