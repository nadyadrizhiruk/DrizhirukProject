package com.drizhiruk.services.order_input;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.validators.ValidationService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ValidationService validationService;

    public OrderServiceImpl(OrderDao orderDao, ValidationService validationService) {
        this.orderDao = orderDao;
        this.validationService = validationService;
    }

    /**
     * method find and return order object  by id
     * @param id  order id
     * @return order by id or null
     */
    @Override
    public Order findById(long id) {
        return orderDao.findById(id);
    }

    /**
     * modify fields of order object
     * @param order order object for modifying
     * @param client new client of order
     * @param date new date of order
     * @param products new products list of order
     */
    @Override
    public void modifyOrder(Order order, Client client, String date, List<Product> products) {

        order.setClient(client);
        order.setDate(date);
        order.setProducts(products);
        saveOrder(order);
    }

    /**
     * method for saving order
     * @param order object of order for saving
     */
    @Override
    public void saveOrder(Order order) {

        boolean result = orderDao.saveOrder(order);
        if (result) {
            System.out.println("Order " + order + "saved");
        }
    }

    /**
     * method delete element from product list
     * @param products list to delete from
     * @param product element to delete
     * @return list of products
     */
    @Override
    public List<Product> deleteElementFromProductList(List<Product> products, Product product) {
        if (products.remove(product)){
            System.out.println("element was removed");
        } else {
            System.out.println("removing was unsuccessful");
        }
        return  products;
    }

    /**
     * method add element in product list
     * @param products list where to add
     * @param product element to add
     * @return list of products
     */
    @Override
    public List<Product> AddElementInProductList(List<Product> products, Product product) {
        if (products.add(product)){
            System.out.println("element was add");
        } else {
            System.out.println("adding was unsuccessful");
        }
        return  products;
    }

    /**
     * method remove order
     * @param id order id
     * @return successful (true) or unsuccessful(false) result of removing
     */
    @Override
    public boolean removeOrder(long id) {
        return orderDao.removeOrder(id);
    }

    /**
     * method find all orders by client
     * @param client client to find orders
     * @return list of orders
     */
    @Override
    public List<Order> findAllOrdersByClient(Client client) {
        return orderDao.findAllOrdersByClient(client);
    }

    /**
     * method create object of Order and save it
     * @param client client of order
     * @param data date of order
     * @param productList list of products
     * @return object of Order
     */
    @Override
    public Order createOrder(Client client, String data, List<Product> productList) {
        Order order = new Order(client,data,productList);
        saveOrder(order);
        return order;
    }
}
