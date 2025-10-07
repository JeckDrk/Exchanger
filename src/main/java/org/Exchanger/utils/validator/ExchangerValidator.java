package org.Exchanger.utils.validator;

import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.errors.InputException;
import org.Exchanger.errors.StatusMessage;


public class ExchangerValidator {

    private final static int FIRST_CODE = 3;
    private final static int SECONDE_CODE = 6;

    private static void validateNumb(String bufNumb){
        try {
            double numb = Double.parseDouble(bufNumb);

            if (numb < 0.000001 || numb > 100000000){
                throw new InputException(StatusMessage.NUMBER_FORMAT_ERROR.getJson(), HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException | NullPointerException e) {
            throw new InputException(StatusMessage.NUMBER_FORMAT_ERROR.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static void isEmptyRate(String baseCurrencyCode, String targetCurrencyCode, String buf) {
        if (baseCurrencyCode == null || targetCurrencyCode == null || buf == null
                || baseCurrencyCode.isEmpty() || targetCurrencyCode.isEmpty() || buf.isEmpty()) {
            throw new InputException(StatusMessage.EMPTY_REQUEST.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static void equalCodes(String baseCurrencyCode, String targetCurrencyCode) {
        if (baseCurrencyCode.equals(targetCurrencyCode)) {
            throw new InputException(StatusMessage.EQUAL_CURRENCIES.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static void validateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, String buf){
        isEmptyRate(baseCurrencyCode, targetCurrencyCode, buf);
        equalCodes(baseCurrencyCode, targetCurrencyCode);
        CurrencyValidator.validateCode(baseCurrencyCode);
        CurrencyValidator.validateCode(targetCurrencyCode);
        validateNumb(buf);
    }

    public static void validateCodes(String codes){
        if (codes.length() != 6){
            throw new InputException(StatusMessage.EMPTY_REQUEST.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }

        String baseCurrencyCode = codes.substring(0, FIRST_CODE);
        String targetCurrencyCode = codes.substring(FIRST_CODE, SECONDE_CODE);

        CurrencyValidator.validateCode(baseCurrencyCode);
        CurrencyValidator.validateCode(targetCurrencyCode);
    }

    public static void isEmptyCode(String code){
        if(code.isEmpty()){
            throw new InputException(StatusMessage.EMPTY_REQUEST.getJson(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
