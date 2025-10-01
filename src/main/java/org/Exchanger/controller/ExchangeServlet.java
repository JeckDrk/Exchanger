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
import org.Exchanger.dto.ExchangeDTO;
import org.Exchanger.dto.ExchangeRateDTO;
import org.Exchanger.util.ParserServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {

    private final ExchangeRateDAO DB = new ExchangeRateDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String baseCurrencyCode = request.getParameter("from");
        String targetCurrencyCode = request.getParameter("to");
        String Amount = request.getParameter("amount");
        double amount;

        if(baseCurrencyCode == null || targetCurrencyCode == null || Amount == null){
            StatusMessage.EMPTY_REQUEST.sendError(response);
            return;
        } else if(baseCurrencyCode.equals(targetCurrencyCode)) {
            StatusMessage.EQUAL_CURRENCIES.sendError(response);
            return;
        }

        try {
            amount = ParserServlet.parseDouble(Amount);
        } catch (NumberFormatException e) {
            StatusMessage.NUMBER_FORMAT_ERROR.sendError(response);
            return;
        }

        CurrencyDAO currencyDB = new CurrencyDAO();
        try {
            CurrencyDTO baseCurrency = currencyDB.get(baseCurrencyCode);
            CurrencyDTO targetCurrency = currencyDB.get(targetCurrencyCode);

            ExchangeRateDTO exchangeRate = new ExchangeRateDTO(0, baseCurrency, targetCurrency, 0);
            ExchangeRateDTO revExchangeRate = new ExchangeRateDTO(0, targetCurrency, baseCurrency, 0);

            double rate;
            if(DB.isContains(exchangeRate)) {
                rate = DB.get(exchangeRate).getRate();
            } else if (DB.isContains(revExchangeRate)) {
                rate = 1 / DB.get(revExchangeRate).getRate();
            } else {
                StatusMessage.EXCHANGER_NOT_EXISTS.sendError(response);
                return;
            }

            double convertedAmount = amount * rate;

            ExchangeDTO resultExchange = new ExchangeDTO();
            resultExchange.setBaseCurrency(baseCurrency);
            resultExchange.setTargetCurrency(targetCurrency);
            resultExchange.setRate(rate);
            resultExchange.setAmount(amount);
            resultExchange.setConvertedAmount(convertedAmount);

            StatusMessage.sendOk(response);
            response.getWriter().write(ParserServlet.objToJson(resultExchange));

        } catch (SQLException e){
            StatusMessage.INTERNAL_SERVER_ERROR.sendError(response);
        }
    }

}
