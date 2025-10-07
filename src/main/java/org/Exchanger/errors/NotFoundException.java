package org.Exchanger.errors;

import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.dto.ErrorDTO;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(new ErrorDTO(message,
                HttpServletResponse.SC_NOT_FOUND));
    }
}
