package org.Exchanger.util;

import org.Exchanger.dao.CurrencyDAO;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.dto.ExchangeRateDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParserDB {
    public static CurrencyDTO getCurrency(ResultSet resultSet) throws SQLException {
        CurrencyDTO currency = new CurrencyDTO();

        currency.setId(resultSet.getInt("id"));
        currency.setCode(resultSet.getString("code"));
        currency.setName(resultSet.getString("fullname"));
        currency.setSign(resultSet.getString("sign"));

        return currency;
    }

    public static ExchangeRateDTO getExchangeRate(ResultSet resultSet) throws SQLException {
        ExchangeRateDTO exchangeRate = new ExchangeRateDTO();

        CurrencyDAO currencyDAO = new CurrencyDAO();

        exchangeRate.setId(resultSet.getInt("id"));

        CurrencyDTO baseCurrency = currencyDAO.get(resultSet.getInt("basecurrencyid"));
        exchangeRate.setBaseCurrency(baseCurrency);

        CurrencyDTO targetCurrency = currencyDAO.get(resultSet.getInt("targetcurrencyid"));
        exchangeRate.setTargetCurrency(targetCurrency);

        exchangeRate.setRate(resultSet.getDouble("rate"));

        return exchangeRate;
    }
}
