package com.hdfc.Entities;

import com.hdfc.Enums.AccountType;

import java.math.BigDecimal;

public class SavingsAccount extends Account{
    public SavingsAccount() {
        super();
        setAccountType(AccountType.SAVINGS);
    }

    public SavingsAccount(String accountNo, String customerId, BigDecimal balance, AccountType accountType) {
        super(accountNo, customerId, balance, AccountType.SAVINGS);
    }

   // public static final BigDecimal minimumBalance = new BigDecimal(0);
    @Override
    public BigDecimal getInterestRate() {
        return new BigDecimal(6);
    }

    @Override
    public BigDecimal getMinimumBalance() {
        return new BigDecimal(1000);
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "accountNo='" + getAccountNo() +
                ", customerId='" + getCustomerId() +
                ", balance=" + getBalance()+"}";
    }
}
