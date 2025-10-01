package org.Exchanger.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.Enum.StatusMessage;
import org.Exchanger.dao.CurrencyDAO;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.util.ParserServlet;
import org.Exchanger.validator.CurrencyValidator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyDAO DB = new CurrencyDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<CurrencyDTO> currencies = DB.getAll();

            StatusMessage.sendOk(response);

            if(currencies != null) {
                response.getWriter().write(ParserServlet.objToJson(currencies));
            }
        } catch (SQLException e){
            StatusMessage.INTERNAL_SERVER_ERROR.sendError(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CurrencyDTO currency = ParserServlet.curFromXml(request);
        StatusMessage status = CurrencyValidator.validate(currency, response);

        if(status == StatusMessage.OK) {
            try{
                if(DB.isContain(currency.getCode())) {
                    StatusMessage.CURRENCY_ALREADY_EXISTS.sendError(response);
                } else {
                    StatusMessage.sendCreated(response);
                    DB.insert(currency);
                    response.getWriter().write(ParserServlet.objToJson(currency));
                }
            } catch (SQLException e){
                StatusMessage.INTERNAL_SERVER_ERROR.sendError(response);
            }
        }
    }
}
