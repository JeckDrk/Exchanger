package org.Exchanger.dto;

public class ErrorDTO {
    private String message;
    private int code;
    public ErrorDTO(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ErrorDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
