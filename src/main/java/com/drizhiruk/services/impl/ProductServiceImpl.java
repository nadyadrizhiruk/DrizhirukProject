package com.drizhiruk.services.impl;

import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Product;
import com.drizhiruk.services.ProductService;
import com.drizhiruk.validators.ValidationService;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ValidationService validationService;

    public ProductServiceImpl(ProductDao productDao, ValidationService validationService) {
        this.productDao = productDao;
        this.validationService = validationService;
    }

    @Override
    public void createProduct(String name, BigDecimal price, int amount) {

        Product product = new Product(name, price, amount);
        saveProduct(product);
    }

    @Override
    public void saveProduct(Product product) {
        boolean result = productDao.saveProduct(product);
        if (result) {
            System.out.println("Product " + product + " was saved");
        }
    }

    @Override
    public Product findById(long id) {
        return productDao.findById(id);
    }

    @Override
    public void modifyProduct(Product product, String name, BigDecimal price, Integer amount) {
        product.setName(name);
        product.setPrice(price);
        product.setAmount(amount);
        saveProduct(product);
    }

    @Override
    public boolean removeProduct(long id) {
        return productDao.removeProduct(id);
    }

    @Override
    public List<Product> getListOfAllProducts() {
        return productDao.getListOfAllProducts();
    }
}
