package org.Exchanger.dto;

public class ExchangeRatePostDTO {
    private int id;
    private CurrencyDTO baseCurrency;
    private CurrencyDTO targetCurrency;
    private double rate;

    public ExchangeRatePostDTO() {
    }

    public ExchangeRatePostDTO(int id, CurrencyDTO baseCurrency, CurrencyDTO targetCurrency, double rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRatePostDTO(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency) {
        this.id = 0;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = 0;
    }

    public ExchangeRatePostDTO(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency, double rate) {
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

    public CurrencyDTO getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyDTO baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyDTO getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyDTO targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
