package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public Order findById(long id) {
        return new Order(new Client("Check","",""),"",new ArrayList<Product>());
    }

    @Override
    public boolean saveOrder(Order order) {
        System.out.println("Saving order");
        return true;
    }

    @Override
    public boolean removeOrder(long id) {
        System.out.println("order removing");
        return true;
    }

    @Override
    public List<Order> findAllOrdersByClient(Client client) {
        return new ArrayList<>();
    }
}
