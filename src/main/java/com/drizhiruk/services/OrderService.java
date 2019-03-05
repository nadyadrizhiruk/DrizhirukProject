package com.drizhiruk.services;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    /**
     * method find and return order object  by id
     * @param id  order id
     * @return order by id or null
     */

    Order findById(long id);

    /**
     * modify fields of order object
     * @param order order object for modifying
     */

    void modifyOrder(Order order, Client client, String date);

    /**
     * method for saving order
     * @param order object of order for saving
     */

    void saveNewOrder(Order order);

    /**
     * method delete element from product list
     * @param products list to delete from
     * @param product element to delete
     * @return list of products
     */

    List<ProductInOrder> deleteElementFromProductList(List<ProductInOrder> products, ProductInOrder product);

    /**
     * method add element in product list
     * @param products list where to add
     * @param product element to add
     * @return list of products
     */

    boolean AddElementInProductList(Order order, ProductInOrder product);

    /**
     * method remove order
     * @param id order id
     * @return successful (true) or unsuccessful(false) result of removing
     */

    boolean removeOrder(long id);

    /**
     * method find all orders by client
     * @param client client to find orders
     * @return list of orders
     */

    List<Order> findAllOrdersByClient(Client client);

    /**
     * method create object of Order and save it
     * @param client client of order
     * @param data date of order
     * @param productList list of products
     * @return object of Order
     */

    Order createOrder(Client client, String data, List<ProductInOrder> productList);

    boolean removeProductInOrderById(Order order, long id);

    Order createOrderObject(Client client, String date, List<ProductInOrder> productsInOrder);

    ProductInOrder createProductInOrderObject(Product product, BigDecimal price, int amount, Order order);

}
