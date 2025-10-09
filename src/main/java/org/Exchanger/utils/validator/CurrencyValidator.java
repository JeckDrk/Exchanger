package org.Exchanger.utils.validator;

import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.errors.InputException;
import org.Exchanger.errors.StatusMessage;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class CurrencyValidator {

    private static final Set<String> currencyCodes = new HashSet<String>();
    private static final Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
    static {
        for (Currency currency : availableCurrencies) {
            currencyCodes.add(currency.getCurrencyCode());
        }
    }

    public static void validateCode(String code){
        if(code.isEmpty()){
            throw new InputException(StatusMessage.EMPTY_REQUEST.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
        if (!currencyCodes.contains(code.toUpperCase())) {
            throw new InputException(StatusMessage.UNCORRECTED_CODE.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static void validateName(String name){
        if(!Pattern.matches("^[\\sa-zA-Z]{1,30}$", name)){
            throw new InputException(StatusMessage.UNCORRECTED_NAME.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static void validateSign(String sign){
        if (!Pattern.matches("^.{1,2}$", sign)){
            throw new InputException(StatusMessage.UNCORRECTED_SIGN.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static void isEmptyCurrency(CurrencyDTO currency){
        if (currency.getCode().isEmpty() || currency.getName().isEmpty() || currency.getSign().isEmpty()){
            throw new InputException(StatusMessage.EMPTY_REQUEST.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static void validateCurrencyDTO(CurrencyDTO currency){
        isEmptyCurrency(currency);
        validateCode(currency.getCode());
        validateName(currency.getName());
        validateSign(currency.getSign());
    }
}
