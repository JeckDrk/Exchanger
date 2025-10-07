package org.Exchanger.controller.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.service.CurrenciesService;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.utils.mapper.BaseMapper;
import org.Exchanger.utils.wrapper.ResponseWrapper;
import org.Exchanger.dto.CurrencyDTO;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

    private CurrencyStorage currencyStorage;
    private final CurrenciesService SERVICE = new CurrenciesService();


    @Override
    public void init(ServletConfig config) {
        currencyStorage = (CurrencyStorage) config.getServletContext().getAttribute("currencyStorage");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getPathInfo().substring(1);

        CurrencyDTO currency = SERVICE.getCurrency(code, currencyStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(currency), HttpServletResponse.SC_OK);
    }
}
