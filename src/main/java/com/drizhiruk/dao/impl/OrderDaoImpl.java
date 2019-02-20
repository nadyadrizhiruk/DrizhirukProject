package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {

    private Map<Long, Order> map = new HashMap<>();
    private static int generator = 0;

    @Override
    public Order findById(long id) {
        return new Order(new Client("Check", "", ""), "", new ArrayList<Product>());
    }

    @Override
    public boolean saveOrder(Order order) {
        order.setId(generator++);
        map.put(order.getId(), order);
        System.out.println("Saving order");
        return true;
    }

    @Override
    public boolean removeOrder(long id) {
        if (map.containsKey(id)) {
            map.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Order> findAllOrdersByClient(Client client) {
        return new ArrayList<>(map.values());
    }
}
