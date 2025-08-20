package com.hdfc.Exceptions;

public class InvalidDepositValue extends RuntimeException {
    public InvalidDepositValue(String message) {
        super(message);
    }
}
