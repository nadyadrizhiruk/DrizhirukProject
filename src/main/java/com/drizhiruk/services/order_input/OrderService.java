package com.drizhiruk.services.order_input;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;

import java.util.List;

public interface OrderService {
    Order findById(long id);
    void modifyOrder(Order order, Client client, String date, List<Product> products);
    void saveOrder(Order order);
    List<Product> deleteElementFromProductList(List<Product> products, Product product);
    List<Product> AddElementInProductList(List<Product> products, Product product);
    boolean removeOrder(long id);
    List<Order> findAllOrdersByClient(Client client);
    Order createOrder(Client client, String data, List<Product> productList);
}
