package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class ProductDBDaoImpl implements ProductDao {

    private Connection connection = DBConnectionHolder.connection;

//    @Autowired
    public ProductDBDaoImpl() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS product(\n" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, \n" +
                    "name VARCHAR(50), \n" +
                    "price DECIMAL , AMOUNT INT);");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean saveProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product(name, price, amount) VALUES(?,?,?)");
            preparedStatement.setString(1, product.getName());//? 1
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getAmount());

            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean saveExistingProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product SET name=?, price=?, amount=? WHERE id=?");
            preparedStatement.setString(1, product.getName());//? 1
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getAmount());
            preparedStatement.setLong(4, product.getId());

            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Product findById(long id) {

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE id =" + id + ";");
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);
                int amount = resultSet.getInt(4);

                return new Product(id, name, price, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public boolean removeProduct(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id =" + id + ";");
            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Product> getListOfAllProducts() {
        Statement statement = null;
        List<Product> clients = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product;");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);
                int amount = resultSet.getInt(4);

                clients.add(new Product(id, name, price, amount));
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
