package com.drizhiruk.services.impl;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;
import com.drizhiruk.services.OrderService;
import com.drizhiruk.validators.ValidationService;

import java.math.BigDecimal;
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
    public void modifyOrder(Order order, Client client, String date) {

        order.setClient(client);
        order.setDate(date);
        boolean result = orderDao.saveExistingOrder(order);
        if (result) {
            System.out.println("Order " + order + "saved");
        }
    }

    @Override
    public void saveNewOrder(Order order) {

        boolean result = orderDao.saveNewOrder(order);
        if (result) {
            System.out.println("Order " + order + "saved");
        }
    }

    @Override
    public List<ProductInOrder> deleteElementFromProductList(List<ProductInOrder> products, ProductInOrder product) {
        if (products.remove(product)){
            System.out.println("element was removed");
        } else {
            System.out.println("removing was unsuccessful");
        }
        return  products;
    }

    @Override
    public boolean AddElementInProductList(Order order, ProductInOrder product) {
        order.addToProducts(product);
        return true;
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
    public Order createOrder(Client client, String data, List<ProductInOrder> productList) {
        Order order = new Order(client,data,productList);
        saveNewOrder(order);
        return order;
    }

    @Override
    public boolean removeProductInOrderById(Order order, long id) {
        for (ProductInOrder productInOrder: order.getProducts()){
            if (productInOrder.getId()==id){
                order.removeFromProducts(productInOrder);
                return true;
            }
        }
        return false;
    }

    @Override
    public Order createOrderObject(Client client, String date, List<ProductInOrder> productsInOrder) {

        return new Order(client,date,productsInOrder);

    }

    @Override
    public ProductInOrder createProductInOrderObject(Product product, BigDecimal price, int amount, Order order) {

        return new ProductInOrder(product,price,amount,order);

    }
}
