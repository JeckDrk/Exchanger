package org.Exchanger.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.Enum.StatusMessage;
import org.Exchanger.dao.CurrencyDAO;
import org.Exchanger.dao.ExchangeRateDAO;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.dto.ExchangeRateDTO;
import org.Exchanger.util.ParserServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Double.parseDouble;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private final ExchangeRateDAO DB = new ExchangeRateDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<ExchangeRateDTO> rates = DB.getAllRates();

            StatusMessage.sendOk(response);

            if(rates != null) {
                String json = ParserServlet.objToJson(rates);
                response.getWriter().write(json);
            }
        } catch (SQLException e){
            StatusMessage.INTERNAL_SERVER_ERROR.sendError(response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CurrencyDAO currencyDB = new CurrencyDAO();

        String baseCurrencyCode = request.getParameter("baseCurrencyCode");
        String targetCurrencyCode = request.getParameter("targetCurrencyCode");
        double rate;
        String bufRate = request.getParameter("rate");

        if(baseCurrencyCode == null || targetCurrencyCode == null || bufRate.isEmpty()) {
            StatusMessage.EMPTY_REQUEST.sendError(response);
            return;
        } else if (baseCurrencyCode.equals(targetCurrencyCode)) {
            StatusMessage.EQUAL_CURRENCIES.sendError(response);
            return;
        }

        try {
            rate = ParserServlet.parseDouble(bufRate);
        } catch (NumberFormatException e){
            StatusMessage.NUMBER_FORMAT_ERROR.sendError(response);
            return;
        }

        try{
            CurrencyDTO baseId = currencyDB.get(baseCurrencyCode);
            CurrencyDTO targetId = currencyDB.get(targetCurrencyCode);

            ExchangeRateDTO exchangeRate = new ExchangeRateDTO(0, baseId, targetId, rate);
            ExchangeRateDTO revExchangeRate = new ExchangeRateDTO(0, targetId, baseId, rate);

            if(DB.isContains(exchangeRate) || DB.isContains(revExchangeRate)) {
                StatusMessage.EXCHANGER_ALREADY_EXISTS.sendError(response);
                return;
            }

            DB.insert(exchangeRate);

            StatusMessage.sendCreated(response);
            response.getWriter().write(ParserServlet.objToJson(exchangeRate));

        } catch (SQLException e){
            StatusMessage.INTERNAL_SERVER_ERROR.sendError(response);
        }
    }
}
