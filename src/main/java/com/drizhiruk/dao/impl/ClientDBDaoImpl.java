package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Component
public class ClientDBDaoImpl implements ClientDao {


    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/worckspace/nadya";
    private static final String LOGIN = "DrizhirukProject";
    private static final String PASSWORD = "DrizhirukProject";

    public ClientDBDaoImpl() {
        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS CLIENT(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(20), SURNAME VARCHAR(20), AGE INT, PHONE VARCHAR(20),  EMAIL VARCHAR(50));");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean saveClient(Client client) {
        String request = "INSERT INTO CLIENT(NAME,SURNAME,AGE,PHONE,EMAIL) VALUES(?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, client.getName());//? 1
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setInt(3, client.getAge());
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getEmail());

            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean saveExistingClient(Client client) {
        String request = "UPDATE client SET name=?,surname=?,age=?,phone=?,email=? WHERE id=?;";

        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setInt(3, client.getAge());
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setLong(6, client.getId());//? 1

            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Client findById(long id) {

        String request = "SELECT * FROM CLIENT WHERE ID =" + id + ";";
        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(request);
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String phone = resultSet.getString(5);
                String email = resultSet.getString(6);

                return new Client(id, name, surname, age, email, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean removeClient(long id) {
        String request = "DELETE FROM CLIENT WHERE ID =" + id + ";";

        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Client> getListOfAllClients() {

        List<Client> clients = new ArrayList<Client>();
        try (Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENT;");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String phone = resultSet.getString(5);
                String email = resultSet.getString(6);

                clients.add(new Client(id, name, surname, age, email, phone));
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
