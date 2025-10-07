package org.Exchanger.dto;

public class ExchangeRateGetDTO {
    private int id;
    private String baseCurrency;
    private String targetCurrency;
    private double rate;

    public ExchangeRateGetDTO() {
    }

    public ExchangeRateGetDTO(int id, String baseCurrency, String targetCurrency, double rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRateGetDTO(String baseCurrency, String targetCurrency) {
        this.id = 0;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = 0;
    }

    public ExchangeRateGetDTO(String baseCurrency, String targetCurrency, double rate) {
        this.id = 0;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
