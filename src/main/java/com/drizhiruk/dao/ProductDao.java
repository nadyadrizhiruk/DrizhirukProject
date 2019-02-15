package com.drizhiruk.dao;

import com.drizhiruk.domain.Product;

import java.util.List;

public interface ProductDao {
    boolean saveProduct(Product product);
    Product findById(long id);
    boolean removeProduct(long id);
    List<Product> getListOfAllProducts();
}
