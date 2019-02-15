package com.drizhiruk.services.product_input;

import com.drizhiruk.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void createProduct(String name, BigDecimal price, int amount);
    void saveProduct(Product product);
    Product findById(long id);
    void modifyProduct(Product product, String name, BigDecimal price, Integer amount);
    boolean removeProduct(long id);
    List<Product> getListOfAllProducts();
}
