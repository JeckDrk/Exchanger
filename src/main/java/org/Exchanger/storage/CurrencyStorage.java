package org.Exchanger.storage;

import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.errors.ApplicationException;
import org.Exchanger.entity.Currency;

import java.util.List;

public interface CurrencyStorage {
    public List<Currency> getAll() throws ApplicationException;

    public Currency get(int id) throws ApplicationException;

    public Currency get(String code) throws ApplicationException;

    public void insert(String code, String name, String sign) throws ApplicationException;
}
