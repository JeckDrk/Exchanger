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

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyDAO DB = new CurrencyDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getPathInfo().substring(1);

        if(code.isEmpty()){
            StatusMessage.BAD_REQUEST.sendError(response);
        } else{
            try {
                CurrencyDTO currency = DB.get(code);
                StatusMessage status = CurrencyValidator.validate(currency, response);
                if(status == StatusMessage.OK){
                    StatusMessage.sendOk(response);
                    response.getWriter().write(ParserServlet.objToJson(currency));
                }
            } catch (SQLException e) {
                StatusMessage.INTERNAL_SERVER_ERROR.sendError(response);
            }
        }
    }
}
