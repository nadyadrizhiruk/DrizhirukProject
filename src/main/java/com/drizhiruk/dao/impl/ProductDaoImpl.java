//package com.drizhiruk.dao.impl;
//
//import com.drizhiruk.dao.ProductDao;
//import com.drizhiruk.domain.Client;
//import com.drizhiruk.domain.Product;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ProductDaoImpl implements ProductDao {
//
//    private Map<Long, Product> map = new HashMap<>();
//    private static int generator = 0;
//
//    @Override
//    public boolean saveProduct(Product product) {
//        product.setId(generator++);
//        map.put(product.getId(), product);
//        return true;
//    }
//
//    @Override
//    public Product findById(long id) {
//
//        return map.get(id);
//    }
//
//    @Override
//    public boolean removeProduct(long id) {
//        if (map.containsKey(id)) {
//            map.remove(id);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public List<Product> getListOfAllProducts() {
//
//        return new ArrayList<>(map.values());
//    }
//}
