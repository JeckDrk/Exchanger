package org.Exchanger.Enum;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public enum StatusMessage {

    OK("", HttpServletResponse.SC_OK),
    CREATED("", HttpServletResponse.SC_CREATED),
    DB_NOT_RESPONSE("База данных не отвечает", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    NOT_FOUND("Совпадений не найдено", HttpServletResponse.SC_NOT_FOUND),
    INTERNAL_SERVER_ERROR("Внутренняя ошибка", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    BAD_REQUEST("Неправильный запрос", HttpServletResponse.SC_BAD_REQUEST),
    EMPTY_REQUEST("Пустой запрос :|", HttpServletResponse.SC_BAD_REQUEST),
    UNCORRECTED_NAME("Длина имени должна быть от 1 до 30 латинских символов", HttpServletResponse.SC_BAD_REQUEST),
    UNCORRECTED_CODE("Код должен быть реально существующим и состоять из 3 латинских символов", HttpServletResponse.SC_BAD_REQUEST),
    UNCORRECTED_SIGN("Знак должен содержать от 1 до 2 символов", HttpServletResponse.SC_BAD_REQUEST),
    CURRENCY_ALREADY_EXISTS("Валюта с таким кодом уже существует", HttpServletResponse.SC_CONFLICT),
    EXCHANGER_ALREADY_EXISTS("Валютная пара с таким кодом уже существует", HttpServletResponse.SC_CONFLICT),
    EXCHANGER_NOT_EXISTS("Одна (или обе) валюта из валютной пары не существует в БД", HttpServletResponse.SC_BAD_REQUEST),
    EQUAL_CURRENCIES("Валюты должны быть разными", HttpServletResponse.SC_BAD_REQUEST),
    NUMBER_FORMAT_ERROR("Число мне введи от 0.000001 до 100'000'000", HttpServletResponse.SC_BAD_REQUEST),
    ;


    StatusMessage(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return "{\"message\": \"" + message + "\"}";
    }

    public void sendError(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(getMessage());
    }

    public static void sendOk(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(OK.status);
    }

    public static void sendCreated(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(CREATED.status);
    }

    private final String message;
    private final int status;
}
