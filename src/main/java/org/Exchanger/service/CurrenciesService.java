package org.Exchanger.service;

import org.Exchanger.errors.*;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.utils.validator.CurrencyValidator;
import org.Exchanger.utils.validator.ExchangerValidator;

import java.util.List;

public class CurrenciesService {

    public CurrencyDTO getCurrency(String code, CurrencyStorage storage) throws StorageException {

        ExchangerValidator.isEmptyCode(code);

        return storage.get(code);
    }

    public List<CurrencyDTO> getAllCurrencies(CurrencyStorage storage) throws StorageException {
        return storage.getAll();
    }

    public CurrencyDTO addCurrency(CurrencyDTO currency, CurrencyStorage storage) throws StorageException, UniqueException {

        CurrencyValidator.validateCurrency(currency);

        storage.insert(currency);

        return storage.get(currency.getCode());
    }
}
