package org.Exchanger.validator;

import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.Enum.StatusMessage;
import org.Exchanger.dto.CurrencyDTO;

import java.io.IOException;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;

public class CurrencyValidator {

    private static final Set<String> currencyCodes = new HashSet<String>();
    private static final Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
    static {
        for (Currency currency : availableCurrencies) {
            currencyCodes.add(currency.getCurrencyCode());
        }
    }

    public static StatusMessage validate(CurrencyDTO currency, HttpServletResponse response) throws IOException {
        if(currency == null) {
            StatusMessage.NOT_FOUND.sendError(response);
        } else if (!currencyCodes.contains(currency.getCode().toUpperCase())) {
            StatusMessage.UNCORRECTED_CODE.sendError(response);
        } else if (!Pattern.matches("^[\\sa-zA-Z]{1,30}$", currency.getName())) {
            StatusMessage.UNCORRECTED_NAME.sendError(response);
        } else if (!Pattern.matches("^.{1,2}$", currency.getSign())) {
            StatusMessage.UNCORRECTED_SIGN.sendError(response);
        } else {
            return StatusMessage.OK;
        }
        return null;
    }
}
