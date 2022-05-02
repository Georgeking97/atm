package com.springboot.first.app.model;

import lombok.Getter;
import lombok.Setter;

public class Account {
    @Getter @Setter
    private int balance;
    @Getter @Setter
    private int accountNumber;
    @Getter @Setter
    private boolean overdraftAccess;
    @Getter @Setter
    private int overdraftBalance;
    @Getter @Setter
    private int accountPin;

    public Account(int accountNumber, int accountPin, int balance, boolean overdraftAccess, int overdraftBalance){
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.overdraftAccess = overdraftAccess;
        this.overdraftBalance = overdraftBalance;
        this.accountPin = accountPin;
    }
}
