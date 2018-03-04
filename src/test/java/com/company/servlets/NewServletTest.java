package com.company.servlets;

import com.company.dao.AccountDao;
import com.company.dataSets.AccountDataSet;
import com.company.service.AccountService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class NewServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    AccountService accountService = mock(AccountService.class);

    Map<String, AccountDataSet> accounts = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        accounts.put("Ivan Petrov", new AccountDataSet("Ivan", "Petrov"));
        accounts.put("Nikolay Smirnov", new AccountDataSet("Nikolay", "Smirnov"));
    }

    // Проверка работы метода doPost при передаче в request существующих
    // в базе firstname и lastname для получения учетной записи
    @Test
    public void doPost() throws Exception {

        NewServlet servlet = new NewServlet();

        String firstName = "Ivan";
        String lastName = "Petrov";

        servlet.service = accountService;

        when(request.getParameter("firstname")).thenReturn(firstName);
        when(request.getParameter("lastname")).thenReturn(lastName);
        when(response.getWriter()).thenReturn(new PrintWriter("Text"));
        when(accountService.getAccount(firstName, lastName)).thenReturn(accounts.get(firstName + " " + lastName));

        servlet.doPost(request, response);

        verify(accountService, atLeastOnce()).getAccount(firstName, lastName);
        assertEquals("Ivan", servlet.accountDataSet.getFirstName());
        assertEquals("Petrov", servlet.accountDataSet.getLastName());
    }

    // Проверка работы метода doPost при передаче в request firstname и lastname
    // учетной записи, которой не существует в базе данных
    @Test
    public void doPost2() throws Exception {
        NewServlet servlet = new NewServlet();

        String firstName = "Sergey";
        String lastName = "Ivanov";

        servlet.service = accountService;

        when(request.getParameter("firstname")).thenReturn(firstName);
        when(request.getParameter("lastname")).thenReturn(lastName);
        when(response.getWriter()).thenReturn(new PrintWriter("Text"));
        when(accountService.getAccount(firstName, lastName)).thenReturn(accounts.get(firstName + " " + lastName));

        servlet.doPost(request, response);

        assertTrue(servlet.accountDataSet == null);
    }

    // Изменение фамилии у существующей учетной записи
    @Test
    public void doPost3() throws Exception {
        NewServlet servlet = new NewServlet();

        String firstName = "Nikolay";
        String lastName = "Smirnov";
        String newLastName = "Volkov";

        servlet.service = accountService;

        when(request.getParameter("firstname")).thenReturn(firstName);
        when(request.getParameter("lastname")).thenReturn(lastName);
        when(request.getParameter("newlastname")).thenReturn(newLastName);
        when(response.getWriter()).thenReturn(new PrintWriter("Text"));
        when(accountService.getAccount(firstName, lastName)).thenReturn(accounts.get(firstName + " " + lastName));
        when(accountService.getAccount(firstName, newLastName)).thenReturn(accounts.get(firstName + " " + newLastName));


        assertTrue(servlet.service.getAccount(firstName, lastName) != null);
        assertTrue(servlet.service.getAccount(firstName, newLastName) == null);

        servlet.doPost(request, response);

        verify(accountService, atLeastOnce()).changeLastName(firstName, lastName, newLastName);

    }
}