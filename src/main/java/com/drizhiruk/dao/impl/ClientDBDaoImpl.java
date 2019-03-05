package com.drizhiruk.dao.impl;

import com.drizhiruk.dao.ClientDao;
import com.drizhiruk.domain.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDBDaoImpl implements ClientDao {


    private Connection connection = DBConnectionHolder.connection;

    public ClientDBDaoImpl() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS CLIENT(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(20), SURNAME VARCHAR(20), AGE INT, PHONE VARCHAR(20),  EMAIL VARCHAR(50));");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean saveClient(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CLIENT(NAME,SURNAME,AGE,PHONE,EMAIL) VALUES(?,?,?,?,?)");
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
    public Client findById(long id) {

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENT WHERE ID =" + id + ";");
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
    public boolean removeClient(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CLIENT WHERE ID =" + id + ";");
            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Client> getListOfAllClients() {
        Statement statement = null;
        List<Client> clients = new ArrayList<>();
        try {
            statement = connection.createStatement();
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
