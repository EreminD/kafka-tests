package com.example;

import java.util.Objects;

public class LoanInfo {
    private Price basePrice;
    private double totalPrice;

    public LoanInfo() {
    }

    public LoanInfo(Price basePrice, double totalPrice) {
        this.basePrice = basePrice;
        this.totalPrice = totalPrice;
    }

    public Price getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Price basePrice) {
        this.basePrice = basePrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanInfo loanInfo)) return false;
        return Double.compare(loanInfo.getTotalPrice(), getTotalPrice()) == 0 && Objects.equals(getBasePrice(), loanInfo.getBasePrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBasePrice(), getTotalPrice());
    }

    @Override
    public String toString() {
        return "LoanInfo{" +
                "basePrice=" + basePrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
