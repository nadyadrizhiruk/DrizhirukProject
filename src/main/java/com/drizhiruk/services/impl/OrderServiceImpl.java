package com.drizhiruk.services.impl;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.validators.ValidationService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ValidationService validationService;

    public OrderServiceImpl(OrderDao orderDao, ValidationService validationService) {
        this.orderDao = orderDao;
        this.validationService = validationService;
    }

    @Override
    public Order findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public void modifyOrder(Order order, Client client, String date, List<Product> products) {

        order.setClient(client);
        order.setDate(date);
        order.setProducts(products);
        saveOrder(order);
    }

    @Override
    public void saveOrder(Order order) {

        boolean result = orderDao.saveOrder(order);
        if (result) {
            System.out.println("Order " + order + "saved");
        }
    }

    @Override
    public List<Product> deleteElementFromProductList(List<Product> products, Product product) {
        if (products.remove(product)){
            System.out.println("element was removed");
        } else {
            System.out.println("removing was unsuccessful");
        }
        return  products;
    }

    @Override
    public List<Product> AddElementInProductList(List<Product> products, Product product) {
        if (products.add(product)){
            System.out.println("element was add");
        } else {
            System.out.println("adding was unsuccessful");
        }
        return  products;
    }

    @Override
    public boolean removeOrder(long id) {
        return orderDao.removeOrder(id);
    }

    @Override
    public List<Order> findAllOrdersByClient(Client client) {
        return orderDao.findAllOrdersByClient(client);
    }

    @Override
    public Order createOrder(Client client, String data, List<Product> productList) {
        Order order = new Order(client,data,productList);
        saveOrder(order);
        return order;
    }
}
