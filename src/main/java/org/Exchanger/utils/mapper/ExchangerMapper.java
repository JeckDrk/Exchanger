package org.Exchanger.utils.mapper;

import org.Exchanger.errors.StatusMessage;
import org.Exchanger.storage.CurrencyDAO;
import org.Exchanger.entity.Currency;
import org.Exchanger.entity.ExchangeRate;
import org.Exchanger.errors.NotFoundException;
import org.Exchanger.storage.CurrencyStorage;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ExchangerMapper {

    public static ExchangeRate getExchangeRate(ResultSet resultSet, CurrencyStorage currencyStorage) throws NotFoundException {
        try {
            ExchangeRate exchangeRate = new ExchangeRate();

            Currency baseCurrency = currencyStorage.get(resultSet.getInt("basecurrencyid"));
            Currency targetCurrency = currencyStorage.get(resultSet.getInt("targetcurrencyid"));

            exchangeRate.setId(resultSet.getInt("id"));
            exchangeRate.setBaseCurrency(baseCurrency);
            exchangeRate.setTargetCurrency(targetCurrency);
            exchangeRate.setRate(resultSet.getDouble("rate"));

            return exchangeRate;
        } catch (SQLException | NullPointerException | NotFoundException e) {
            throw new NotFoundException(StatusMessage.NOT_FOUND_EXCHANGER.getJson());
        }
    }
}
