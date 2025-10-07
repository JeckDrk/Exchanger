package org.Exchanger.storage;

import org.Exchanger.dto.ExchangeRateGetDTO;
import org.Exchanger.dto.ExchangeRatePostDTO;
import org.Exchanger.errors.ApplicationException;
import org.Exchanger.errors.NotFoundException;
import org.Exchanger.errors.StorageException;
import org.Exchanger.errors.UniqueException;

import java.util.List;

public interface ExchangerStorage {
    public List<ExchangeRatePostDTO> getAll() throws ApplicationException;

    public ExchangeRatePostDTO get(ExchangeRateGetDTO exchangeRate)throws ApplicationException;

    public void insert(ExchangeRateGetDTO exchangeRate) throws ApplicationException;

    public void update(ExchangeRateGetDTO exchangeRate) throws ApplicationException;
}
