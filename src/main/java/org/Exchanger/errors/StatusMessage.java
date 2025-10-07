package org.Exchanger.errors;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public enum StatusMessage {

    INTERNAL_SERVER_ERROR("Внутренняя ошибка"),
    NOT_FOUND_EXCHANGER("Одна (или обе) валюта из валютной пары не существует в БД"),
    NOT_FOUND_CURRENCY("Нет подходящей валюты"),
    EMPTY_REQUEST("Пустой запрос :|"),
    UNCORRECTED_NAME("Длина имени должна быть от 1 до 30 латинских символов"),
    UNCORRECTED_CODE("Код должен быть реально существующим и состоять из 3 латинских символов"),
    UNCORRECTED_SIGN("Знак должен содержать от 1 до 2 символов"),
    UNIQUE_ERROR("Уже существует"),
    EQUAL_CURRENCIES("Валюты должны быть разными"),
    NUMBER_FORMAT_ERROR("Число мне введи от 0.000001 до 100'000'000"),
    ;

    StatusMessage(String message) {
        this.message = message;
    }

    public String getJson() {
        return "{\"message\": \"" + message + "\"}";
    }

    private final String message;
}
