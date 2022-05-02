package com.springboot.first.app.model;

import lombok.Getter;
import lombok.Setter;

public class Withdraw {
    @Getter @Setter
    private int withdrawAmount;
    @Getter @Setter
    private int accountPin;

    public Withdraw(int withdrawAmount, int accountPin) {
        this.withdrawAmount = withdrawAmount;
        this.accountPin = accountPin;
    }
}
