package org.Exchanger.service;

import org.Exchanger.dto.ExchangeRateGetDTO;
import org.Exchanger.dto.ExchangeRatePostDTO;
import org.Exchanger.errors.*;
import org.Exchanger.storage.ExchangerStorage;
import org.Exchanger.utils.validator.ExchangerValidator;

import java.util.List;


public class ExchangeRateService {

    private static final int FIRST_CODE = 3;
    private static final int SECONDE_CODE = 6;

    public List<ExchangeRatePostDTO> getAllExchangeRates(ExchangerStorage exchangerStorage) throws StorageException, NotFoundException {
        return exchangerStorage.getAll();
    }

    public ExchangeRatePostDTO getExchangeRate(String codes, ExchangerStorage exchangerStorage)
            throws ApplicationException {

        ExchangerValidator.validateCodes(codes);

        String baseCurrencyCode = codes.substring(0, FIRST_CODE);
        String targetCurrencyCode = codes.substring(FIRST_CODE, SECONDE_CODE);

        ExchangeRateGetDTO exchangeRate = new ExchangeRateGetDTO(baseCurrencyCode, targetCurrencyCode);

        return exchangerStorage.get(exchangeRate);
    }

    public ExchangeRatePostDTO insertExchangeRate(String baseCurCode, String tarCurCode, String bufRate, ExchangerStorage exchangerStorage)
            throws ApplicationException {

        ExchangerValidator.validateExchangeRate(baseCurCode, tarCurCode, bufRate);

        double rate = Double.parseDouble(bufRate);

        ExchangeRateGetDTO exchangeRate = new ExchangeRateGetDTO(baseCurCode, tarCurCode, rate);
        ExchangeRateGetDTO revExchangeRate = new ExchangeRateGetDTO(tarCurCode, baseCurCode, rate);

        try {
            exchangerStorage.get(revExchangeRate);
            throw new UniqueException();
        } catch (NotFoundException e) {
            exchangerStorage.insert(exchangeRate);
            return exchangerStorage.get(exchangeRate);
        }
    }

    public ExchangeRatePostDTO updateExchangeRate(String codes, String bufRate, ExchangerStorage exchangerStorage)
            throws ApplicationException {

        ExchangerValidator.validateCodes(codes);

        String baseCurrencyCode = codes.substring(0, FIRST_CODE);
        String targetCurrencyCode = codes.substring(FIRST_CODE, SECONDE_CODE);

        ExchangerValidator.validateExchangeRate(baseCurrencyCode, targetCurrencyCode, bufRate);

        double rate = Double.parseDouble(bufRate);

        ExchangeRateGetDTO exchangeRate = new ExchangeRateGetDTO(baseCurrencyCode, targetCurrencyCode, rate);

        exchangerStorage.update(exchangeRate);

        return exchangerStorage.get(exchangeRate);
    }
}
