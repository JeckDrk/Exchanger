package org.Exchanger.utils.mapper;

import jakarta.servlet.http.HttpServletRequest;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.errors.NotFoundException;
import org.Exchanger.errors.StatusMessage;
import org.Exchanger.entity.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper {

    public static CurrencyDTO DtoFromUrl(HttpServletRequest request) {
        CurrencyDTO cur = new CurrencyDTO();

        cur.setCode(request.getParameter("code"));
        cur.setName(request.getParameter("name"));
        cur.setSign(request.getParameter("sign"));

        return cur;
    }

    public static Currency getCurrency(ResultSet resultSet) throws NotFoundException {
        try {
            Currency currency = new Currency();

            currency.setId(resultSet.getInt("id"));
            currency.setCode(resultSet.getString("code"));
            currency.setName(resultSet.getString("fullname"));
            currency.setSign(resultSet.getString("sign"));

            if (currency.getCode().isEmpty()){
                throw new NotFoundException(StatusMessage.NOT_FOUND_CURRENCY.getJson());
            }

            return currency;
        } catch (SQLException | NullPointerException e) {
            throw new NotFoundException(StatusMessage.NOT_FOUND_CURRENCY.getJson());
        }
    }
}
