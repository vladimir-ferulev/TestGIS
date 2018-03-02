package com.company.servlets;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class NewServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    // Проверка работы метода doPost при передаче в request существующих
    // в базе firstname и lastname для получения учетной записи
    @Test
    public void doPost() throws Exception {
        NewServlet servlet = new NewServlet();

        when(request.getParameter("firstname")).thenReturn("Ivan");
        when(request.getParameter("lastname")).thenReturn("Petrov");
        when(response.getWriter()).thenReturn(new PrintWriter("Text"));

        servlet.doPost(request, response);

        assertEquals(servlet.accountDataSet.getFirstName(), "Ivan");
        assertEquals(servlet.accountDataSet.getLastName(), "Petrov");
    }

    // Проверка работы метода doPost при передаче в request firstname и lastname
    // учетной записи, которой не существует в базе данных
    @Test
    public void doPost2() throws Exception {
        NewServlet servlet = new NewServlet();

        when(request.getParameter("firstname")).thenReturn("Sergey");
        when(request.getParameter("lastname")).thenReturn("Ivanov");
        when(response.getWriter()).thenReturn(new PrintWriter("Text"));

        servlet.doPost(request, response);

        assertTrue(servlet.accountDataSet == null);
    }

    // Изменение фамилии у существующей учетной записи
    @Test
    public void doPost3() throws Exception {
        NewServlet servlet = new NewServlet();

        when(request.getParameter("firstname")).thenReturn("Nikolay");
        when(request.getParameter("lastname")).thenReturn("Ivanov");
        when(request.getParameter("newlastname")).thenReturn("Petrov");
        when(response.getWriter()).thenReturn(new PrintWriter("Text"));

        assertTrue(servlet.service.getAccount("Nikolay", "Petrov") == null);

        servlet.doPost(request, response);

        assertTrue(servlet.service.getAccount("Nikolay", "Petrov") != null);
    }
}