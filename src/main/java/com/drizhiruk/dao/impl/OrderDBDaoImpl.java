package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.OrderDao;
import com.drizhiruk.domain.Client;
import com.drizhiruk.domain.Order;
import com.drizhiruk.domain.Product;
import com.drizhiruk.domain.ProductInOrder;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDBDaoImpl implements OrderDao {

    private Connection connection = DBConnectionHolder.connection;

    public OrderDBDaoImpl() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ORDERS(\n" +
                    "ID_ORDER BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "DATA VARCHAR(50),\n" +
                    "ID_ORDER_CLIENT BIGINT,\n" +
                    "FOREIGN KEY (ID_ORDER_CLIENT) REFERENCES CLIENT (ID)\n" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCTS_IN_ORDERS(\n" +
                    "ID_PRODUCTS_IN_ORDERS BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "ID_PRODUCTS_IN_ORDERS_PRODUCT BIGINT,\n" +
                    "FOREIGN KEY (ID_PRODUCTS_IN_ORDERS_PRODUCT) REFERENCES PRODUCT (ID_PRODUCT),\n" +
                    "ID_PRODUCTS_IN_ORDERS_ORDER BIGINT,\n" +
                    "FOREIGN KEY (ID_PRODUCTS_IN_ORDERS_ORDER) REFERENCES ORDERS (ID_ORDER),\n" +
                    "AMOUNT INT,\n" +
                    "PRICE DECIMAL\n" +
                    ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findById(long id) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT \n" +
                    "orders.data AS orders_date ,\n" +
                    "client.id AS client_id,\n" +
                    "client.name  client_name,\n" +
                    "client.surname AS client_surname,\n" +
                    "client.age AS client_age,\n" +
                    "client.phone AS client_phone,\n" +
                    "client.email AS client_email,\n" +
                    "product.id_product AS product_id,\n" +
                    "product.name  AS product_name,\n" +
                    "product.price AS product_price,\n" +
                    "product.amount AS product_amount,\n" +
                    "products_in_orders .amount AS products_amount,\n" +
                    "products_in_orders.price  AS  products_price,\n" +
                    "products_in_orders.id_products_in_orders  AS  products_id,\n" +
                    "orders.id_order  AS  order_id\n" +
                    "FROM orders\n" +
                    "LEFT JOIN client  ON orders.id_order_client = client .id\n" +
                    "LEFT JOIN products_in_orders   ON ORDERS .id_order  = products_in_orders .id_products_in_orders_order\n" +
                    "LEFT JOIN product  ON products_in_orders.id_products_in_orders_product   = product.id_product\n" +
                    "WHERE orders.id_order = " + id + "\n" +
                    "ORDER BY orders.id_order ;\n");
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
                    ProductInOrder productInOrder = new ProductInOrder(productsId,product,productsPrice,productsAmount,order);
                    order.addToProducts(productInOrder);
                }
            }

            return order;

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
    public boolean saveExistingOrder(Order order) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products_in_orders  WHERE ID_PRODUCTS_IN_ORDERS_ORDER =" + order.getId() + ";");
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE id_order =" + order.getId() + ";");
            preparedStatement.execute();


            preparedStatement = connection.prepareStatement("INSERT INTO orders(id_order, data, id_order_client) VALUES(?,?,?)");
            preparedStatement.setLong(1, order.getId());
            preparedStatement.setString(2, order.getDate());
            preparedStatement.setLong(3, order.getClient().getId());
            preparedStatement.execute();

            preparedStatement.clearBatch();
            preparedStatement = connection.prepareStatement("SELECT \n" +
                    "data as date,\n" +
                    "id_order_client as client_id,\n" +
                    "MAX(id_order) as last_id\n" +
                    "FROM orders\n" +
                    "WHERE data = '" + order.getDate() + "' AND id_order_client=" + order.getClient().getId() + "\n" +
                    "GROUP BY data,  id_order_client");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long orderId = resultSet.getLong("last_id");
                for (ProductInOrder productInOrder : order.getProducts()) {
                    if (productInOrder.getId()!=0){
                    preparedStatement = connection.prepareStatement("INSERT INTO products_in_orders(ID_PRODUCTS_IN_ORDERS_PRODUCT, ID_PRODUCTS_IN_ORDERS_ORDER) VALUES(?,?)");
                    preparedStatement.setLong(1, productInOrder.getProduct().getId());//? 1
                    preparedStatement.setLong(2, orderId);
                    preparedStatement.execute();} else{
                        preparedStatement = connection.prepareStatement("INSERT INTO products_in_orders(ID_PRODUCTS_IN_ORDERS, ID_PRODUCTS_IN_ORDERS_PRODUCT, ID_PRODUCTS_IN_ORDERS_ORDER) VALUES(?,?)");
                        preparedStatement.setLong(1, productInOrder.getId());//? 1
                        preparedStatement.setLong(2, productInOrder.getProduct().getId());
                        preparedStatement.setLong(3, orderId);
                        preparedStatement.execute();
                    }

                }
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
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders(data, id_order_client) VALUES(?,?)");
            preparedStatement.setString(1, order.getDate());//? 1
            preparedStatement.setLong(2, order.getClient().getId());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("SELECT \n" +
                    "data as date,\n" +
                    "id_order_client as client_id,\n" +
                    "MAX(id_order) as last_id\n" +
                    "FROM orders\n" +
                    "WHERE data = '" + order.getDate() + "' AND id_order_client=" + order.getClient().getId() + "\n" +
                    "GROUP BY data,  id_order_client");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long orderId = resultSet.getLong("last_id");
                for (ProductInOrder productInOrder : order.getProducts()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO products_in_orders(ID_PRODUCTS_IN_ORDERS_PRODUCT, ID_PRODUCTS_IN_ORDERS_ORDER) VALUES(?,?)");
                    preparedStatement.setLong(1, productInOrder.getProduct().getId());//? 1
                    preparedStatement.setLong(2, orderId);
                    preparedStatement.execute();
                }
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
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products_in_orders  WHERE ID_PRODUCTS_IN_ORDERS_ORDER =" + id + ";");
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE id_order =" + id + ";");
            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Order> findAllOrdersByClient(Client client) {
        List<Order> orders = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT \n" +
                    "orders.data AS orders_date ,\n" +
                    "client.id AS client_id,\n" +
                    "client.name  client_name,\n" +
                    "client.surname AS client_surname,\n" +
                    "client.age AS client_age,\n" +
                    "client.phone AS client_phone,\n" +
                    "client.email AS client_email,\n" +
                    "product.id_product AS product_id,\n" +
                    "product.name  AS product_name,\n" +
                    "product.price AS product_price,\n" +
                    "product.amount AS product_amount,\n" +
                    "products_in_orders .amount AS products_amount,\n" +
                    "products_in_orders.price  AS  products_price,\n" +
                    "products_in_orders.id_products_in_orders  AS  products_id,\n" +
                    "orders.id_order  AS  order_id\n" +
                    "FROM orders\n" +
                    "LEFT JOIN client  ON orders.id_order_client = client .id\n" +
                    "LEFT JOIN products_in_orders   ON ORDERS .id_order  = products_in_orders .id_products_in_orders_order\n" +
                    "LEFT JOIN product  ON products_in_orders.id_products_in_orders_product   = product.id_product\n" +
                    "WHERE orders.id_order_client = " + client.getId() + "\n" +
                    "ORDER BY orders.id_order ;\n");
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
                    ProductInOrder productInOrder = new ProductInOrder(productsId,product,productsPrice,productsAmount,order);
                    order.addToProducts(productInOrder);
                }
            }
            return orders;
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

        return orders;
    }
}
