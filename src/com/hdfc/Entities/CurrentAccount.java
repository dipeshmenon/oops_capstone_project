package com.hdfc.Entities;

import com.hdfc.Enums.AccountType;

import java.math.BigDecimal;

public class CurrentAccount extends Account{

   // public static final BigDecimal interestRate = new BigDecimal(4);
    //public static final BigDecimal minimumBalance = new BigDecimal(0);

    public CurrentAccount() {
        super();
        setAccountType(AccountType.CURRENT);
    }

    public CurrentAccount(String accountNo, String customerId, AccountType accountType,BigDecimal balance) {
        super(accountNo, customerId, balance,  AccountType.CURRENT);
    }

    @Override
    public BigDecimal getInterestRate() {
        return new BigDecimal(4);
    }

    @Override
    public BigDecimal getMinimumBalance() {
        return new BigDecimal(0);
    }


    @Override
    public String toString() {
        return "CurrentAccount{" +
                "accountNo='" + getAccountNo() +
                ", customerId='" + getCustomerId() +
                ", balance=" + getBalance()+"}";
    }


}
