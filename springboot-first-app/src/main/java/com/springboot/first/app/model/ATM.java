package com.springboot.first.app.model;

import lombok.Getter;
import lombok.Setter;

public class ATM {
    @Getter @Setter
    int totalBalance;
    @Getter @Setter
    int fifty;
    @Getter @Setter
    int twenty;
    @Getter @Setter
    int ten;
    @Getter @Setter
    int five;

    public ATM(int totalBalance, int fifty, int twenty, int ten, int five) {
        this.totalBalance = totalBalance;
        this.fifty = fifty;
        this.twenty = twenty;
        this.ten = ten;
        this.five = five;
    }
}
