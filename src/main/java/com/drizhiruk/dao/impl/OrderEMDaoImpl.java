package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Component
public class OrderEMDaoImpl implements OrderDao {

    private EntityManagerFactory factory;

    @Autowired
    public OrderEMDaoImpl() {
        factory = Persistence.createEntityManagerFactory("persistence-unit");
    }


    @Override
    public Order findById(long id) {

        EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(Order.class, id);
    }

    @Override
    public boolean saveExistingOrder(Order order) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        for(ProductInOrder productInOrder:order.getProducts()){
            entityManager.merge(productInOrder);
        }
        entityManager.merge(order);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    @Override
    public boolean saveNewOrder(Order order) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(order);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    @Override
    public boolean removeOrder(long id) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM ProductInOrder WHERE order.id= :ordId").setParameter("ordId", id).executeUpdate();
        entityManager.createQuery("DELETE FROM Order WHERE id=" + id).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    @Override
    public List<Order> findAllOrdersByClient(Client client) {

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Order> orders = entityManager.createQuery("SELECT ord FROM Order ord WHERE ord.client = :client", Order.class)
                .setParameter("client", client)
                .getResultList();
        return orders;
    }
}
