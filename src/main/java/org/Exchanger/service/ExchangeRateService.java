package org.Exchanger.service;

import org.Exchanger.dto.ExchangeRateDTO;
import org.Exchanger.entity.ExchangeRate;
import org.Exchanger.errors.*;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.storage.ExchangerStorage;

import java.util.List;


public class ExchangeRateService {

    public List<ExchangeRate> getAllExchangeRates(ExchangerStorage exchangerStorage, CurrencyStorage currencyStorage)
            throws StorageException, NotFoundException {

        return exchangerStorage.getAll(currencyStorage);
    }

    public ExchangeRate getExchangeRate(ExchangeRateDTO exchangeRate, ExchangerStorage exchangerStorage,
                                        CurrencyStorage currencyStorage)
            throws ApplicationException {
        return exchangerStorage.get(exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency(), currencyStorage);
    }

    public ExchangeRate insertExchangeRate(ExchangeRateDTO exchangeRate, ExchangerStorage exchangerStorage,
                                           CurrencyStorage currencyStorage)
            throws ApplicationException {

        String baseCurrency = exchangeRate.getBaseCurrency();
        String targetCurrency = exchangeRate.getTargetCurrency();
        double rate = exchangeRate.getRate();

        try {
            exchangerStorage.get(targetCurrency, baseCurrency, currencyStorage);
            throw new UniqueException();
        } catch (NotFoundException e) {
            exchangerStorage.insert(baseCurrency, targetCurrency, rate);

            return exchangerStorage.get(baseCurrency, targetCurrency, currencyStorage);
        }
    }

    public ExchangeRate updateExchangeRate(ExchangeRateDTO exchangeRate, ExchangerStorage exchangerStorage,
                                           CurrencyStorage currencyStorage)
            throws ApplicationException {

        String baseCurrency = exchangeRate.getBaseCurrency();
        String targetCurrency = exchangeRate.getTargetCurrency();
        double rate = exchangeRate.getRate();

        exchangerStorage.update(baseCurrency, targetCurrency, rate);

        return exchangerStorage.get(baseCurrency, targetCurrency, currencyStorage);
    }
}
