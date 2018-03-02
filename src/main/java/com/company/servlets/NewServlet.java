package com.company.servlets;

import com.company.dataSets.AccountDataSet;
import com.company.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class NewServlet extends HttpServlet {

    AccountService service;
    AccountDataSet accountDataSet;

    public NewServlet() {
        service = new AccountService();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String newLastName = request.getParameter("newlastname");
        try {
            if (newLastName != null) {
                if (service.getAccount(firstName, lastName) != null) {
                    if(service.getAccount(firstName, newLastName) != null)
                    {
                        response.getWriter().println("Account with the same name already exists");
                    } else {
                        service.changeLastName(firstName, lastName, newLastName);
                        response.getWriter().println("Last name is changed");
                    }
                } else {
                    response.getWriter().println("Account not found");
                }

            } else {
                accountDataSet = service.getAccount(firstName, lastName);
                if (accountDataSet != null) {
                    response.getWriter().println(accountDataSet.toString());
                } else {
                    response.getWriter().println("Account not found");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
