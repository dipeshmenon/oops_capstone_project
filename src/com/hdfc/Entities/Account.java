package com.hdfc.Entities;

import com.hdfc.Enums.AccountType;
import com.hdfc.Exceptions.InsufficientBalanceException;
import com.hdfc.Exceptions.InvalidDepositValue;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Account {
    private String accountNo;
    private String customerId;
    private BigDecimal balance;
    private AccountType accountType;

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Account(String accountNo, String customerId, BigDecimal balance, AccountType accountType) {
        this.accountNo = accountNo;
        this.customerId = customerId;
        this.balance = balance;
    }

    public Account() {

    }



    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNo='" + accountNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNo, account.accountNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNo);
    }

    public void deposit(BigDecimal amount){

        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new InvalidDepositValue("Deposit value is invalid - must be greater than 0");
        }

        this.balance = this.balance.add(amount);
    }
    public void withdraw(BigDecimal amount){

        if(amount.compareTo(balance)>0){

            throw new InsufficientBalanceException("Withdrawal not possible - Insufficient balance");

        }
        this.balance = this.balance.subtract(amount);
    }

    public abstract BigDecimal getInterestRate();
    public abstract BigDecimal getMinimumBalance();

    public BigDecimal calculateInterest(){
        return (getInterestRate().multiply(balance)).divide(new BigDecimal(100));
    }


}
