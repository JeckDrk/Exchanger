package org.Exchanger.dto;

public class ExchangeDTO {
    private CurrencyDTO baseCurrency;
    private CurrencyDTO targetCurrency;
    private double rate;
    private double amount;
    private double convertedAmount;

    public ExchangeDTO() {
    }

    public ExchangeDTO(ExchangeRatePostDTO exchangeRate, double amount, double convertedAmount) {
        this.baseCurrency = exchangeRate.getBaseCurrency();
        this.targetCurrency = exchangeRate.getTargetCurrency();
        this.rate = exchangeRate.getRate();
        this.amount = amount;
        this.convertedAmount = convertedAmount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}
