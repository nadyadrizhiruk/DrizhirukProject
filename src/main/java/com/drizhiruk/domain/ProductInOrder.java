package com.drizhiruk.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTS_IN_ORDERS")
public class ProductInOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "PRODUCT_ID ",referencedColumnName = "ID")
    private Product product;

    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "amount")
    private int amount;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "ORDER_ID ",referencedColumnName = "ID")
    private Order order;

    public ProductInOrder() {
    }

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
