package org.Exchanger.errors;

import org.Exchanger.dto.ErrorDTO;

public class InputException extends ApplicationException {
    public InputException(String message, int code) {
        super(new ErrorDTO(message, code));
    }
}
