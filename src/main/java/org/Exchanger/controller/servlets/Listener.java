package org.Exchanger.controller.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.Exchanger.storage.CurrencyDAO;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.storage.ExchangeRateDAO;
import org.Exchanger.storage.ExchangerStorage;

@WebListener("/*")
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        CurrencyStorage currencyStorage = new CurrencyDAO();
        ExchangerStorage exchangerStorage = new ExchangeRateDAO();

        context.setAttribute("currencyStorage", currencyStorage);
        context.setAttribute("exchangerStorage", exchangerStorage);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
