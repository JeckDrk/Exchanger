package org.Exchanger.controller.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.dto.ExchangeDTO;
import org.Exchanger.errors.ApplicationException;
import org.Exchanger.service.ExchangerService;
import org.Exchanger.storage.ExchangerStorage;
import org.Exchanger.utils.mapper.BaseMapper;
import org.Exchanger.utils.wrapper.ResponseWrapper;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {

    private ExchangerStorage exchangerStorage;

    private final ExchangerService SERVICE = new ExchangerService();

    @Override
    public void init(ServletConfig config) {
        exchangerStorage = (ExchangerStorage) config.getServletContext().getAttribute("exchangerStorage");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {
        String baseCurrencyCode = request.getParameter("from");
        String targetCurrencyCode = request.getParameter("to");
        String amountBuffer = request.getParameter("amount");

        ExchangeDTO exchangeDTO = SERVICE.getExchange(baseCurrencyCode, targetCurrencyCode, amountBuffer, exchangerStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(exchangeDTO), HttpServletResponse.SC_OK);
    }

}
