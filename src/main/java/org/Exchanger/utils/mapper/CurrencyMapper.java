package org.Exchanger.utils.mapper;

import jakarta.servlet.http.HttpServletRequest;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.errors.NotFoundException;
import org.Exchanger.errors.StatusMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper {

    public static CurrencyDTO curFromUrl(HttpServletRequest request) {
        CurrencyDTO cur = new CurrencyDTO();

        cur.setCode(request.getParameter("code"));
        cur.setName(request.getParameter("name"));
        cur.setSign(request.getParameter("sign"));

        return cur;
    }

    public static CurrencyDTO getCurrency(ResultSet resultSet) throws NotFoundException {
        try {
            CurrencyDTO currency = new CurrencyDTO();

            currency.setId(resultSet.getInt("id"));
            currency.setCode(resultSet.getString("code"));
            currency.setName(resultSet.getString("fullname"));
            currency.setSign(resultSet.getString("sign"));

            return currency;
        } catch (SQLException | NullPointerException e) {
            throw new NotFoundException(StatusMessage.NOT_FOUND_CURRENCY.getJson());
        }
    }
}
