package org.Exchanger.errors;

import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.dto.ErrorDTO;

public class StorageException extends ApplicationException {
    public StorageException() {
        super(new ErrorDTO(StatusMessage.INTERNAL_SERVER_ERROR.getJson(),
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
    }
}
