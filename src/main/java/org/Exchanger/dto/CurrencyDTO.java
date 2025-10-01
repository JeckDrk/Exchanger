package org.Exchanger.dto;

public class CurrencyDTO {
    private int id;
    private String code;
    private String name;
    private String sign;

    public CurrencyDTO() {
    }

    public CurrencyDTO(int id, String code, String name, String sign) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fullName='" + name + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

}
