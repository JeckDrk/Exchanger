package org.Exchanger.controller.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.dto.ExchangeRateDTO;
import org.Exchanger.entity.Currency;
import org.Exchanger.errors.*;
import org.Exchanger.service.ExchangeRateService;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.storage.ExchangerStorage;
import org.Exchanger.entity.ExchangeRate;
import org.Exchanger.utils.mapper.BaseMapper;
import org.Exchanger.utils.validator.ExchangerValidator;
import org.Exchanger.utils.wrapper.ResponseWrapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private ExchangerStorage exchangerStorage;
    private CurrencyStorage currencyStorage;

    private final ExchangeRateService SERVICE = new ExchangeRateService();

    @Override
    public void init(ServletConfig config) {
        exchangerStorage = (ExchangerStorage) config.getServletContext().getAttribute("exchangerStorage");
        currencyStorage = (CurrencyStorage) config.getServletContext().getAttribute("currencyStorage");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {

        List<ExchangeRate> rates = SERVICE.getAllExchangeRates(exchangerStorage, currencyStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(rates), HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {

        String baseCurCode = request.getParameter("baseCurrencyCode");
        String targetCurCode = request.getParameter("targetCurrencyCode");
        String bufRate = request.getParameter("rate");

        ExchangerValidator.validateExchangeRateDTO(baseCurCode, targetCurCode, bufRate);

        double rate = Double.parseDouble(bufRate);

        ExchangeRateDTO exchangeRate = new ExchangeRateDTO(baseCurCode, targetCurCode, rate);

        ExchangeRate responseDTO = SERVICE.insertExchangeRate(exchangeRate, exchangerStorage, currencyStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(responseDTO), HttpServletResponse.SC_CREATED);
    }
}
