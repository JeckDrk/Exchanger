package org.Exchanger.errors;

import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.dto.ErrorDTO;

public class UniqueException extends ApplicationException {
    public UniqueException() {
        super(new ErrorDTO(StatusMessage.UNIQUE_ERROR.getJson(), HttpServletResponse.SC_CONFLICT));
    }
}
