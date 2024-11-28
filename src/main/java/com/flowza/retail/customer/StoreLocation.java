package com.flowza.retail.customer;

public enum StoreLocation {
    STORE_A("01"),
    STORE_B("02"),
    STORE_C("03");

    private String value;

    StoreLocation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
