package com.ridesharing.app.enums;

public enum BillConfig {
    PER_MINUTE_CHARGE(2.00),
    PER_KILOMETRE_CHARGE(6.50),
    BASE_FARE_CHARGE(50.00),
    SERVICE_TAX_CHARGE(0.20);

    private final double value;
    BillConfig(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
