package org.Exchanger.utils.wrapper;

import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.dto.ErrorDTO;

import java.io.IOException;

public class ResponseWrapper {
    public static void send(HttpServletResponse response, ErrorDTO error) throws IOException {
        response.setStatus(error.getCode());
        response.getWriter().write(error.getMessage());
    }

    public static void send(HttpServletResponse response, String massage, int code) throws IOException {
        response.setStatus(code);
        response.getWriter().write(massage);
    }
}
