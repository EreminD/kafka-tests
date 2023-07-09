package com.example;

import java.util.Objects;

public class DepositInfo {
    private Price basePrice;
    private double rate;

    public DepositInfo() {
    }

    public DepositInfo(Price basePrice, double rate) {
        this.basePrice = basePrice;
        this.rate = rate;
    }

    public Price getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Price basePrice) {
        this.basePrice = basePrice;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepositInfo that)) return false;
        return Double.compare(that.getRate(), getRate()) == 0 && Objects.equals(getBasePrice(), that.getBasePrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBasePrice(), getRate());
    }

    @Override
    public String toString() {
        return "DepositInfo{" +
                "basePrice=" + basePrice +
                ", rate=" + rate +
                '}';
    }
}
