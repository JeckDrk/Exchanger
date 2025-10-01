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

import static java.lang.Double.parseDouble;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateDAO DB = new ExchangeRateDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codes = request.getPathInfo().substring(1);

        if (codes.length() != 6){
            StatusMessage.BAD_REQUEST.sendError(response);
            return;
        }

        String baseCurrencyCode = ParserServlet.splitToPair(codes)[0];
        String targetCurrencyCode = ParserServlet.splitToPair(codes)[1];

        try {
            CurrencyDAO currencyDB = new CurrencyDAO();

            if(!currencyDB.isContain(baseCurrencyCode) || !currencyDB.isContain(targetCurrencyCode)){
                StatusMessage.EXCHANGER_NOT_EXISTS.sendError(response);
                return;
            }

            CurrencyDTO baseCurrency = currencyDB.get(baseCurrencyCode);
            CurrencyDTO targetCurrency = currencyDB.get(targetCurrencyCode);

            if(!DB.isContains(new ExchangeRateDTO(0,baseCurrency,targetCurrency,0))){
                StatusMessage.NOT_FOUND.sendError(response);
                return;
            }

            ExchangeRateDTO exchangeRate = DB.get(baseCurrency, targetCurrency);

            StatusMessage.sendOk(response);
            response.getWriter().write(ParserServlet.objToJson(exchangeRate));

        } catch (SQLException e){
            StatusMessage.INTERNAL_SERVER_ERROR.sendError(response);
        }
    }
    
    @Override
    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String codes = request.getPathInfo().substring(1);

        if (codes.length() != 6){
            StatusMessage.BAD_REQUEST.sendError(response);
            return;
        }

        String baseCurrencyCode = ParserServlet.splitToPair(codes)[0];
        String targetCurrencyCode = ParserServlet.splitToPair(codes)[1];

        double rate;

        String bufRate = ParserServlet.paramFromXml(request, "rate");

        try {
            rate = ParserServlet.parseDouble(bufRate);
        } catch (NumberFormatException e){
            StatusMessage.NUMBER_FORMAT_ERROR.sendError(response);
            return;
        }

        try {
            CurrencyDAO currencyDB = new CurrencyDAO();

            if(!currencyDB.isContain(baseCurrencyCode) || !currencyDB.isContain(targetCurrencyCode)){
                StatusMessage.EXCHANGER_NOT_EXISTS.sendError(response);
                return;
            }

            CurrencyDTO baseCurrency = currencyDB.get(baseCurrencyCode);
            CurrencyDTO targetCurrency = currencyDB.get(targetCurrencyCode);

            ExchangeRateDTO exchangeRate = new ExchangeRateDTO(0,baseCurrency,targetCurrency,rate);

            if(!DB.isContains(exchangeRate)){
                StatusMessage.NOT_FOUND.sendError(response);
                return;
            }

            DB.update(exchangeRate);

            StatusMessage.sendOk(response);
            response.getWriter().write(ParserServlet.objToJson(exchangeRate));

        } catch (SQLException e){
            StatusMessage.INTERNAL_SERVER_ERROR.sendError(response);
        }
    }
}
