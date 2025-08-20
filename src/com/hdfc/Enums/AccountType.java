package com.hdfc.Enums;

public enum AccountType {
    SAVINGS("savings"), CURRENT("current");

    private String type;

    AccountType(String type) {
        this.type = type;
    }
}
