package org.Exchanger.storage;

import org.Exchanger.dto.ExchangeRateDTO;
import org.Exchanger.entity.ExchangeRate;
import org.Exchanger.errors.ApplicationException;

import java.util.List;

public interface ExchangerStorage {
    public List<ExchangeRate> getAll(CurrencyStorage currencyStorage) throws ApplicationException;

    public ExchangeRate get(String baseCurrencyCode, String targetCurrencyCode, CurrencyStorage currencyStorage)throws ApplicationException;

    public void insert(String baseCurrencyCode, String targetCurrencyCode, double rate) throws ApplicationException;

    public void update(String baseCurrencyCode, String targetCurrencyCode, double rate) throws ApplicationException;
}
