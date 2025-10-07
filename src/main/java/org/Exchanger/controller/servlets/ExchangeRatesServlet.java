package org.Exchanger.controller.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.errors.*;
import org.Exchanger.service.ExchangeRateService;
import org.Exchanger.storage.ExchangerStorage;
import org.Exchanger.dto.ExchangeRatePostDTO;
import org.Exchanger.utils.mapper.BaseMapper;
import org.Exchanger.utils.wrapper.ResponseWrapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private ExchangerStorage exchangerStorage;

    private final ExchangeRateService SERVICE = new ExchangeRateService();

    @Override
    public void init(ServletConfig config) {
        exchangerStorage = (ExchangerStorage) config.getServletContext().getAttribute("exchangerStorage");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {
        List<ExchangeRatePostDTO> rates = SERVICE.getAllExchangeRates(exchangerStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(rates), HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {
        String baseCurCode = request.getParameter("baseCurrencyCode");
        String targetCurCode = request.getParameter("targetCurrencyCode");
        String bufRate = request.getParameter("rate");

        ExchangeRatePostDTO exchangeRate = SERVICE.insertExchangeRate(baseCurCode, targetCurCode, bufRate, exchangerStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(exchangeRate), HttpServletResponse.SC_CREATED);
    }
}
