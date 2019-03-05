package com.drizhiruk.dao;

import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;

import java.util.List;

public interface OrderDao {
    Order findById(long id);
    boolean saveExistingOrder(Order order);
    boolean saveNewOrder(Order order);
    boolean removeOrder(long id);
    List<Order> findAllOrdersByClient(Client client);
}
