package org.Exchanger.controller.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.utils.mapper.BaseMapper;
import org.Exchanger.utils.validator.CurrencyValidator;
import org.Exchanger.utils.wrapper.ResponseWrapper;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.service.CurrenciesService;
import org.Exchanger.utils.mapper.CurrencyMapper;
import org.Exchanger.entity.Currency;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrenciesService SERVICE = new CurrenciesService();

    private CurrencyStorage currencyStorage;

    @Override
    public void init(ServletConfig config) {
        currencyStorage = (CurrencyStorage) config.getServletContext().getAttribute("currencyStorage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Currency> currencies = SERVICE.getAllCurrencies(currencyStorage);

        response.getWriter().write(BaseMapper.toJson(currencies));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        CurrencyDTO currencyDTO = CurrencyMapper.DtoFromUrl(request);

        CurrencyValidator.validateCurrencyDTO(currencyDTO);

        Currency currency = SERVICE.addCurrency(currencyDTO, currencyStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(currency), HttpServletResponse.SC_CREATED);
    }
}
