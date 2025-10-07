package org.Exchanger.service;

import org.Exchanger.dto.ExchangeDTO;
import org.Exchanger.dto.ExchangeRateGetDTO;
import org.Exchanger.dto.ExchangeRatePostDTO;
import org.Exchanger.errors.ApplicationException;
import org.Exchanger.errors.NotFoundException;
import org.Exchanger.storage.ExchangerStorage;
import org.Exchanger.utils.validator.ExchangerValidator;

public class ExchangerService {
    public ExchangeDTO getExchange(String baseCurCode, String targCurCode, String amountBuf, ExchangerStorage exchangerStorage)
            throws ApplicationException {

        ExchangerValidator.validateExchangeRate(baseCurCode, targCurCode, amountBuf);

        double amount = Double.parseDouble(amountBuf);

        ExchangeRateGetDTO rateGetDTO = new ExchangeRateGetDTO(baseCurCode, targCurCode);
        ExchangeRateGetDTO revRateGetDTO = new ExchangeRateGetDTO(targCurCode, baseCurCode);

        ExchangeRatePostDTO response;
        double rate;
        try {
            response = exchangerStorage.get(rateGetDTO);
            rate = response.getRate();
            return new ExchangeDTO(response, amount, amount * rate);
        } catch (NotFoundException ignored) {}

        response = exchangerStorage.get(revRateGetDTO);
        rate = 1 / response.getRate();
        response.setRate(rate);

        return new ExchangeDTO(response, amount, amount * rate);
    }
}
