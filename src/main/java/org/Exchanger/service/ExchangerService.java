package org.Exchanger.service;

import org.Exchanger.dto.ExchangerDTO;
import org.Exchanger.entity.Exchange;
import org.Exchanger.entity.ExchangeRate;
import org.Exchanger.errors.ApplicationException;
import org.Exchanger.errors.NotFoundException;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.storage.ExchangerStorage;

public class ExchangerService {
    public Exchange getExchange(ExchangerDTO exchangerDTO, ExchangerStorage exchangerStorage,
                                CurrencyStorage currencyStorage) throws ApplicationException {

        String baseCurrencyCode = exchangerDTO.getBaseCurrency();
        String targetCurrencyCode = exchangerDTO.getTargetCurrency();
        double amount = exchangerDTO.getAmount();

        try {
            ExchangeRate exchanger = exchangerStorage.get(baseCurrencyCode, targetCurrencyCode, currencyStorage);

            double rate = exchanger.getRate();

            return new Exchange(exchanger, amount, amount * rate);
        } catch (NotFoundException ignored) {}

        try {
            ExchangeRate revExchanger = exchangerStorage.get(targetCurrencyCode, baseCurrencyCode, currencyStorage);

            double rate = 1 / revExchanger.getRate();
            revExchanger.setRate(rate);

            return new Exchange(revExchanger, amount, amount * rate);
        } catch (NotFoundException ignored){}

        ExchangeRate usdBase = exchangerStorage.get("USD", baseCurrencyCode, currencyStorage);
        ExchangeRate usdTarg = exchangerStorage.get("USD", targetCurrencyCode, currencyStorage);

        double rate = usdTarg.getRate() / usdBase.getRate();

        Exchange exchangerUSD = new Exchange();
        exchangerUSD.setBaseCurrency(usdBase.getTargetCurrency());
        exchangerUSD.setTargetCurrency(usdTarg.getTargetCurrency());
        exchangerUSD.setRate(rate);
        exchangerUSD.setAmount(amount);
        exchangerUSD.setConvertedAmount(amount * rate);

        return exchangerUSD;
    }
}
