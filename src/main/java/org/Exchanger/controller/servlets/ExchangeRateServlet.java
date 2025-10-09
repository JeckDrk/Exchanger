package org.Exchanger.controller.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.dto.ExchangeRateDTO;
import org.Exchanger.errors.*;
import org.Exchanger.service.ExchangeRateService;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.storage.ExchangerStorage;
import org.Exchanger.entity.ExchangeRate;
import org.Exchanger.utils.mapper.BaseMapper;
import org.Exchanger.utils.validator.ExchangerValidator;
import org.Exchanger.utils.wrapper.ResponseWrapper;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private ExchangerStorage exchangerStorage;
    private CurrencyStorage currencyStorage;

    private final ExchangeRateService SERVICE = new ExchangeRateService();

    private static final int FIRST_CODE = 3;
    private static final int SECONDE_CODE = 6;

    @Override
    public void init(ServletConfig config) {
        exchangerStorage = (ExchangerStorage) config.getServletContext().getAttribute("exchangerStorage");
        currencyStorage = (CurrencyStorage) config.getServletContext().getAttribute("currencyStorage");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {

        String codes = request.getPathInfo().substring(1);
        ExchangerValidator.validateCodes(codes);

        String baseCurrencyCode = codes.substring(0, FIRST_CODE);
        String targetCurrencyCode = codes.substring(FIRST_CODE, SECONDE_CODE);

        ExchangeRateDTO exchangeRate = new ExchangeRateDTO(baseCurrencyCode, targetCurrencyCode);

        ExchangeRate responseExchangeRate = SERVICE.getExchangeRate(exchangeRate, exchangerStorage, currencyStorage);
        ResponseWrapper.send(response, BaseMapper.toJson(responseExchangeRate), HttpServletResponse.SC_OK);
    }
    
    @Override
    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {

        String codes = request.getPathInfo().substring(1);
        ExchangerValidator.validateCodes(codes);

        String bufRate = BaseMapper.paramFromUrl(request, "rate");
        String baseCurrencyCode = codes.substring(0, FIRST_CODE);
        String targetCurrencyCode = codes.substring(FIRST_CODE, SECONDE_CODE);

        ExchangerValidator.validateExchangeRateDTO(baseCurrencyCode, targetCurrencyCode, bufRate);

        double rate = Double.parseDouble(bufRate);

        ExchangeRateDTO exchangeRate = new ExchangeRateDTO(baseCurrencyCode, targetCurrencyCode, rate);

        ExchangeRate responseDTO = SERVICE.updateExchangeRate(exchangeRate, exchangerStorage, currencyStorage);
        ResponseWrapper.send(response, BaseMapper.toJson(responseDTO), HttpServletResponse.SC_OK);
    }
}
