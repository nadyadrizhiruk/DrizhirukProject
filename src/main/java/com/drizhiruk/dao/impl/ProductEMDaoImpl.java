package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Component
public class ProductEMDaoImpl implements ProductDao {

    private EntityManagerFactory factory;

    @Autowired
    public ProductEMDaoImpl() {
        factory = Persistence.createEntityManagerFactory("persistence-unit");
    }

    @Override
    public boolean saveProduct(Product product) {

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    @Override
    public boolean saveExistingProduct(Product product) {
        saveProduct(product);
        return false;
    }

    @Override
    public Product findById(long id) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(Product.class, id);
    }

    @Override
    public boolean removeProduct(long id) {

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Product WHERE id=" + id).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }

    @Override
    public List<Product> getListOfAllProducts() {

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Product> products = entityManager.createQuery("from Product", Product.class).getResultList();
        return products;
    }
}
