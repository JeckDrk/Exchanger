package org.Exchanger.storage;

import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.errors.ApplicationException;
import org.Exchanger.errors.NotFoundException;
import org.Exchanger.errors.StorageException;
import org.Exchanger.errors.UniqueException;

import java.util.List;

public interface CurrencyStorage {
    public List<CurrencyDTO> getAll() throws ApplicationException;

    public CurrencyDTO get(int id) throws ApplicationException;

    public CurrencyDTO get(String code) throws ApplicationException;

    public void insert(CurrencyDTO currencyDTO) throws ApplicationException;
}
