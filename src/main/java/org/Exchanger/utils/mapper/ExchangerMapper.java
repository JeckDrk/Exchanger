package org.Exchanger.utils.mapper;

import org.Exchanger.errors.StatusMessage;
import org.Exchanger.storage.CurrencyDAO;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.dto.ExchangeRatePostDTO;
import org.Exchanger.errors.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangerMapper {

    public static ExchangeRatePostDTO getExchangeRate(ResultSet resultSet) throws NotFoundException {
        try {
            ExchangeRatePostDTO exchangeRate = new ExchangeRatePostDTO();

            CurrencyDAO currencyDAO = new CurrencyDAO();

            exchangeRate.setId(resultSet.getInt("id"));

            CurrencyDTO baseCurrency = currencyDAO.get(resultSet.getInt("basecurrencyid"));
            exchangeRate.setBaseCurrency(baseCurrency);

            CurrencyDTO targetCurrency = currencyDAO.get(resultSet.getInt("targetcurrencyid"));
            exchangeRate.setTargetCurrency(targetCurrency);

            exchangeRate.setRate(resultSet.getDouble("rate"));

            return exchangeRate;
        } catch (SQLException | NullPointerException e) {
            throw new NotFoundException(StatusMessage.NOT_FOUND_EXCHANGER.getJson());
        }
    }
}
