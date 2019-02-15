package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

//    private final List<Product> products = new ArrayList<>();

    @Override
    public boolean saveProduct(Product product) {
        System.out.println("Saving product");
        return true;
    }

    @Override
    public Product findById(long id) {

        return new Product("Check",new BigDecimal(0),0);
    }

    @Override
    public boolean removeProduct(long id) {
        System.out.println("product removing");
        return true;
    }

    @Override
    public List<Product> getListOfAllProducts() {

        return new ArrayList<>();
    }
}
