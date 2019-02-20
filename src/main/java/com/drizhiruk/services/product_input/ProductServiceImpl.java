package com.drizhiruk.services.product_input;

import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Product;
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

    /**
     * method create object Product and save it
     * @param name name of product
     * @param price price of product
     * @param amount amount of product
     */
    @Override
    public void createProduct(String name, BigDecimal price, int amount) {

        Product product = new Product(name, price, amount);
        saveProduct(product);
    }

    /**
     * method for saving product
     * @param product product for saving
     */
    @Override
    public void saveProduct(Product product) {
        boolean result = productDao.saveProduct(product);
        if (result) {
            System.out.println("Product " + product + " was saved");
        }
    }

    /**
     * method find and return Product object  by id
     * @param id id of product
     * @return product by id or null
     */
    @Override
    public Product findById(long id) {
        return productDao.findById(id);
    }

    /**
     * method modify fields of Product object
     * @param product new product
     * @param name new name
     * @param price new price
     * @param amount new amount
     */
    @Override
    public void modifyProduct(Product product, String name, BigDecimal price, Integer amount) {
        product.setName(name);
        product.setPrice(price);
        product.setAmount(amount);
        saveProduct(product);
    }

    /**
     * method remove product
     * @param id product id
     * @return successful (true) or unsuccessful(false) result of removing
     */
    @Override
    public boolean removeProduct(long id) {
        return productDao.removeProduct(id);
    }

    /**
     * method for getting list of all products
     * @return list of products
     */
    @Override
    public List<Product> getListOfAllProducts() {
        return productDao.getListOfAllProducts();
    }
}
