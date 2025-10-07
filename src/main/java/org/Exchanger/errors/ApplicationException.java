package org.Exchanger.errors;

import org.Exchanger.dto.ErrorDTO;

public class ApplicationException extends RuntimeException {
    ErrorDTO error;

    public ApplicationException(ErrorDTO error) {
        super(error.getMessage());
        this.error = error;
    }

    public ErrorDTO getError() {
        return error;
    }
}
