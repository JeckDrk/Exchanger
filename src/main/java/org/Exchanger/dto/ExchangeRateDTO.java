package org.Exchanger.dto;

public class ExchangeRateDTO {
    private int id;
    private String baseCurrency;
    private String targetCurrency;
    private double rate;

    public ExchangeRateDTO() {
    }

    public ExchangeRateDTO(int id, String baseCurrency, String targetCurrency, double rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRateDTO(String baseCurrency, String targetCurrency) {
        this.id = 1;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = 1;
    }

    public ExchangeRateDTO(String baseCurrency, String targetCurrency, double rate) {
        this.id = 1;
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
