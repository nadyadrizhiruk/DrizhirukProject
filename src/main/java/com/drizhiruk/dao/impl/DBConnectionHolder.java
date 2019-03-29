package com.drizhiruk.dao.impl;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
class DBConnectionHolder {

    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/worckspace/nadya";
    private static final String LOGIN = "DrizhirukProject";
    private static final String PASSWORD = "DrizhirukProject";
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
