package com.springboot.first.app.controller;

import com.springboot.first.app.model.Withdraw;
import com.springboot.first.app.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    AccountService accountService = new AccountService();

    // this initializes the ATM account
    @GetMapping("/account")
    public String init() {
        return accountService.totalBalance();
    }

    // this allows a user to withdraw from their account
    @PostMapping("/account/withdraw")
    public String withdraw(@RequestBody Withdraw withdraw) {
        return accountService.withdraw(withdraw);
    }

    // this allows a user to check their balance
    @PostMapping("/account/balance")
    public String userBalance(@RequestBody int pin) {
        int result = accountService.userBalance(pin);
        String returnResult;
        switch (result) {
            case 1:
                returnResult = "Incorrect pin, please try again";
                break;
            case 2:
                returnResult = "No accounts currently exist in our system";
                break;
            default:
                returnResult = "Your balance is: " + result;
        }
        return returnResult;
    }
}
