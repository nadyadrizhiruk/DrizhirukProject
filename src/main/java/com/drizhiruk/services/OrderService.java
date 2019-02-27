package com.drizhiruk.services;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;

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
     * @param client new client of order
     * @param date new date of order
     * @param products new products list of order
     */

    void modifyOrder(Order order, Client client, String date, List<Product> products);

    /**
     * method for saving order
     * @param order object of order for saving
     */

    void saveOrder(Order order);

    /**
     * method delete element from product list
     * @param products list to delete from
     * @param product element to delete
     * @return list of products
     */

    List<Product> deleteElementFromProductList(List<Product> products, Product product);

    /**
     * method add element in product list
     * @param products list where to add
     * @param product element to add
     * @return list of products
     */

    List<Product> AddElementInProductList(List<Product> products, Product product);

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

    Order createOrder(Client client, String data, List<Product> productList);
}
