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

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private ExchangerStorage exchangerStorage;

    private final ExchangeRateService SERVICE = new ExchangeRateService();

    @Override
    public void init(ServletConfig config) {
        exchangerStorage = (ExchangerStorage) config.getServletContext().getAttribute("exchangerStorage");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {
        String codes = request.getPathInfo().substring(1);

        ExchangeRatePostDTO exchangeRate = SERVICE.getExchangeRate(codes, exchangerStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(exchangeRate), HttpServletResponse.SC_OK);
    }
    
    @Override
    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, IOException {
        String codes = request.getPathInfo().substring(1);

        String bufRate = BaseMapper.paramFromUrl(request, "rate");

        ExchangeRatePostDTO exchangeRate = SERVICE.updateExchangeRate(codes, bufRate, exchangerStorage);

        ResponseWrapper.send(response, BaseMapper.toJson(exchangeRate), HttpServletResponse.SC_OK);
    }
}
