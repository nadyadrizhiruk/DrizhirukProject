package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Component
public class OrderDBDaoImpl implements OrderDao {

    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/worckspace/nadya";
    private static final String LOGIN = "DrizhirukProject";
    private static final String PASSWORD = "DrizhirukProject";

    public OrderDBDaoImpl() {
        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS orders(\n" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "date VARCHAR(50),\n" +
                    "client_id BIGINT,\n" +
                    "FOREIGN KEY (client_id) REFERENCES client (id));");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS products_in_orders(\n" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "product_id BIGINT,\n" +
                    "FOREIGN KEY (product_id) REFERENCES product (id),\n" +
                    "order_id BIGINT,\n" +
                    "FOREIGN KEY (order_id) REFERENCES ORDERS (id),\n" +
                    "amount INT,\n" +
                    "price DECIMAL\n" +
                    ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findById(long id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT \n" +
                    "orders.date AS orders_date ,\n" +
                    "client.id AS client_id,\n" +
                    "client.name  client_name,\n" +
                    "client.surname AS client_surname,\n" +
                    "client.age AS client_age,\n" +
                    "client.phone AS client_phone,\n" +
                    "client.email AS client_email,\n" +
                    "product.id AS product_id,\n" +
                    "product.name  AS product_name,\n" +
                    "product.price AS product_price,\n" +
                    "product.amount AS product_amount,\n" +
                    "products_in_orders .amount AS products_amount,\n" +
                    "products_in_orders.price  AS  products_price,\n" +
                    "products_in_orders.id  AS  products_id,\n" +
                    "orders.id  AS  order_id\n" +
                    "FROM orders\n" +
                    "LEFT JOIN client  ON orders.client_id = client .id\n" +
                    "LEFT JOIN products_in_orders   ON ORDERS .id  = products_in_orders .order_id\n" +
                    "LEFT JOIN product  ON products_in_orders.product_id   = product.id\n" +
                    "WHERE orders.id = " + id + "\n" +
                    "ORDER BY orders.id ;\n");
            Order order = null;
            while (resultSet.next()) {
                long orderId = resultSet.getLong("order_id");
                if (order == null) {

                    long clientId = resultSet.getLong("client_id");
                    String clientName = resultSet.getString("client_name");
                    String clientSurname = resultSet.getString("client_surname");
                    int clientAge = resultSet.getInt("client_age");
                    String clientPhone = resultSet.getString("client_phone");
                    String clientEmail = resultSet.getString("client_email");
                    Client client = new Client(clientId, clientName, clientSurname, clientAge, clientPhone, clientEmail);

                    String orderDate = resultSet.getString("orders_date");

                    order = new Order(orderId, client, orderDate);
                }

                String productName = resultSet.getString("product_name");
                if (productName != null) {

                    long productId = resultSet.getLong("product_id");
                    BigDecimal productPrice = resultSet.getBigDecimal("product_price");
                    int productAmount = resultSet.getInt("product_amount");

                    long productsId = resultSet.getLong("products_id");
                    BigDecimal productsPrice = resultSet.getBigDecimal("products_price");
                    int productsAmount = resultSet.getInt("products_amount");

                    Product product = new Product(productId, productName, productPrice, productAmount);
                    ProductInOrder productInOrder = new ProductInOrder(productsId, product, productsPrice, productsAmount, order);
                    order.addToProducts(productInOrder);
                }
            }

            return order;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveExistingOrder(Order order) {

        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD)) {

            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products_in_orders  WHERE order_id =" + order.getId() + ";");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("UPDATE orders SET date=?, client_id=? WHERE id=?;");
            preparedStatement.setString(1, order.getDate());
            preparedStatement.setLong(2, order.getClient().getId());
            preparedStatement.setLong(3, order.getId());

            preparedStatement.execute();

            preparedStatement.clearBatch();
            preparedStatement = connection.prepareStatement("SELECT \n" +
                    "date as date,\n" +
                    "client_id as client_id,\n" +
                    "MAX(id) as last_id\n" +
                    "FROM orders\n" +
                    "WHERE date = '" + order.getDate() + "' AND client_id=" + order.getClient().getId() + "\n" +
                    "GROUP BY date,  client_id");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long orderId = resultSet.getLong("last_id");
                for (ProductInOrder productInOrder : order.getProducts()) {
                    if (productInOrder.getId() == 0) {
                        preparedStatement = connection.prepareStatement("INSERT INTO products_in_orders(product_id, order_id) VALUES(?,?)");
                        preparedStatement.setLong(1, productInOrder.getProduct().getId());//? 1
                        preparedStatement.setLong(2, orderId);
                        preparedStatement.execute();
                    } else {
                        preparedStatement = connection.prepareStatement("INSERT INTO products_in_orders(id, product_id, order_id) VALUES(?,?,?)");
                        preparedStatement.setLong(1, productInOrder.getId());//? 1
                        preparedStatement.setLong(2, productInOrder.getProduct().getId());
                        preparedStatement.setLong(3, orderId);
                        preparedStatement.execute();
                    }

                }
                connection.commit();
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean saveNewOrder(Order order) {
        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD)) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders(date, client_id) VALUES(?,?)");
            preparedStatement.setString(1, order.getDate());//? 1
            preparedStatement.setLong(2, order.getClient().getId());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("SELECT \n" +
                    "date as date,\n" +
                    "client_id as client_id,\n" +
                    "MAX(id) as last_id\n" +
                    "FROM orders\n" +
                    "WHERE date = '" + order.getDate() + "' AND client_id=" + order.getClient().getId() + "\n" +
                    "GROUP BY date,  client_id");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long orderId = resultSet.getLong("last_id");
                for (ProductInOrder productInOrder : order.getProducts()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO products_in_orders(product_id, order_id) VALUES(?,?)");
                    preparedStatement.setLong(1, productInOrder.getProduct().getId());//? 1
                    preparedStatement.setLong(2, orderId);
                    preparedStatement.execute();
                }
                connection.commit();
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean removeOrder(long id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD)) {

            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products_in_orders  WHERE order_id =" + id + ";");
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE id =" + id + ";");
            preparedStatement.execute();
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Order> findAllOrdersByClient(Client client) {

        List<Order> orders = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT \n" +
                    "orders.date AS orders_date ,\n" +
                    "client.id AS client_id,\n" +
                    "client.name  client_name,\n" +
                    "client.surname AS client_surname,\n" +
                    "client.age AS client_age,\n" +
                    "client.phone AS client_phone,\n" +
                    "client.email AS client_email,\n" +
                    "product.id AS product_id,\n" +
                    "product.name  AS product_name,\n" +
                    "product.price AS product_price,\n" +
                    "product.amount AS product_amount,\n" +
                    "products_in_orders .amount AS products_amount,\n" +
                    "products_in_orders.price  AS  products_price,\n" +
                    "products_in_orders.id  AS  products_id,\n" +
                    "orders.id  AS  order_id\n" +
                    "FROM orders\n" +
                    "LEFT JOIN client  ON orders.client_id = client .id\n" +
                    "LEFT JOIN products_in_orders   ON ORDERS .id  = products_in_orders .order_id\n" +
                    "LEFT JOIN product  ON products_in_orders.product_id   = product.id\n" +
                    "WHERE orders.client_id = " + client.getId() + "\n" +
                    "ORDER BY orders.id ;\n");
            Order order = null;

            while (resultSet.next()) {
                long orderId = resultSet.getLong("order_id");
                if ((order == null) || (order.getId() != orderId)) {

                    String orderDate = resultSet.getString("orders_date");

                    order = new Order(orderId, client, orderDate);
                    orders.add(order);
                }

                String productName = resultSet.getString("product_name");
                if (productName != null) {

                    long productId = resultSet.getLong("product_id");
                    BigDecimal productPrice = resultSet.getBigDecimal("product_price");
                    int productAmount = resultSet.getInt("product_amount");

                    long productsId = resultSet.getLong("products_id");
                    BigDecimal productsPrice = resultSet.getBigDecimal("products_price");
                    int productsAmount = resultSet.getInt("products_amount");

                    Product product = new Product(productId, productName, productPrice, productAmount);
                    ProductInOrder productInOrder = new ProductInOrder(productsId, product, productsPrice, productsAmount, order);
                    order.addToProducts(productInOrder);
                }
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
