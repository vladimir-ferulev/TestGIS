package com.company.dao;

import com.company.dataSets.AccountDataSet;
import com.company.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountDao {

    private Executor executor;

    public AccountDao(Connection connection) {
        this.executor = new Executor(connection);
    }

    public AccountDataSet getAccount(String firstName, String lastName) throws SQLException {
        return executor.execQuery("select * from accounts where firstname = \'" + firstName
                + "\' and lastname = \'" + lastName + "\'", resultSet -> {
            if (resultSet.next()) {
                return new AccountDataSet(resultSet.getString("firstname"), resultSet.getString("lastname"));
            }
            return null;
        });
    }

    public void changeLastName(String firstName, String lastName, String newLastName) throws SQLException {
        executor.execUpdate("update accounts set lastname = \'" + newLastName +
                "\' where firstname = \'" + firstName + "\' and lastname = \'" + lastName + "\'");
    }
}
