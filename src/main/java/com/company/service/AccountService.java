package com.company.service;

import com.company.dao.AccountDao;
import com.company.dataSets.AccountDataSet;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountService {
    private final Connection connection;

    public AccountService() {
        this.connection = getMySQLConnection();
    }

    public AccountDataSet getAccount(String firstName, String lastName) throws SQLException {
        try {
            return (new AccountDao(connection).getAccount(firstName, lastName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeLastName(String firstName, String lastName, String newLastName) {
        try {
            AccountDao dao = new AccountDao(connection);
            dao.changeLastName(firstName, lastName, newLastName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getMySQLConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            String jdbcUrl = "jdbc:mysql://localhost:3306/db_accounts?autoReconnect=true&useSSL=false";
            return DriverManager.getConnection(jdbcUrl, "root", "root");
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
