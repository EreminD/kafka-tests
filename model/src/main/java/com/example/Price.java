package com.example;

import java.util.Objects;

public class Price {
    private String currency;
    private double price;

    public Price() {
    }

    public Price(String currency, double price) {
        this.currency = currency;
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price price1)) return false;
        return Double.compare(price1.getPrice(), getPrice()) == 0 && Objects.equals(getCurrency(), price1.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrency(), getPrice());
    }

    @Override
    public String toString() {
        return "Price{" +
                "currency='" + currency + '\'' +
                ", price=" + price +
                '}';
    }
}
