package com.drizhiruk.services;

import com.drizhiruk.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    /**
     * method create object Product and save it
     * @param name name of product
     * @param price price of product
     * @param amount amount of product
     */

    void createProduct(String name, BigDecimal price, int amount);

    /**
     * method for saving product
     * @param product product for saving
     */

    void saveProduct(Product product);

    /**
     * method find and return Product object  by id
     * @param id id of product
     * @return product by id or null
     */

    Product findById(long id);

    /**
     * method modify fields of Product object
     * @param product new product
     * @param name new name
     * @param price new price
     * @param amount new amount
     */

    void modifyProduct(Product product, String name, BigDecimal price, Integer amount);

    /**
     * method remove product
     * @param id product id
     * @return successful (true) or unsuccessful(false) result of removing
     */

    boolean removeProduct(long id);

    /**
     * method for getting list of all products
     * @return list of products
     */

    List<Product> getListOfAllProducts();
}
