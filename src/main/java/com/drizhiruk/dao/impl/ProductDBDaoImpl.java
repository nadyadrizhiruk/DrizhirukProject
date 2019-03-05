package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ProductDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDBDaoImpl implements ProductDao {

    private Connection connection = DBConnectionHolder.connection;

    public ProductDBDaoImpl() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT(ID_PRODUCT BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(50), PRICE DECIMAL , AMOUNT INT);");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean saveProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT(NAME, PRICE, AMOUNT) VALUES(?,?,?)");
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
    public Product findById(long id) {

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE ID_PRODUCT =" + id + ";");
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PRODUCT WHERE ID_PRODUCT =" + id + ";");
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT;");
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
