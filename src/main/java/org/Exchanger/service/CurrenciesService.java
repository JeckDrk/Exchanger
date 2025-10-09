package org.Exchanger.service;

import org.Exchanger.errors.*;
import org.Exchanger.storage.CurrencyStorage;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.entity.Currency;

import java.util.List;

public class CurrenciesService {

    public Currency get(CurrencyDTO currency, CurrencyStorage storage) throws StorageException {
        return storage.get(currency.getCode());
    }

    public List<Currency> getAllCurrencies(CurrencyStorage storage) throws StorageException {
        return storage.getAll();
    }

    public Currency addCurrency(CurrencyDTO cur, CurrencyStorage storage) throws StorageException, UniqueException {

        storage.insert(cur.getCode(), cur.getName(), cur.getSign());

        return storage.get(cur.getCode());
    }
}
