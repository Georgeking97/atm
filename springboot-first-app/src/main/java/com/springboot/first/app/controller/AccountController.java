package com.springboot.first.app.controller;

import com.springboot.first.app.model.Withdraw;
import com.springboot.first.app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    AccountService accountService = new AccountService();

    // this initializes the ATM account
    @GetMapping("/account")
    public ResponseEntity<String> init() {
        return new ResponseEntity<>(accountService.totalBalance(), HttpStatus.CREATED);
    }

    // this allows a user to withdraw from their account
    @PostMapping("/account/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody Withdraw withdraw) {
        String result = accountService.withdraw(withdraw);
        switch (result) {
            case "Please provide a valid amount to withdraw":
                return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
            case "No accounts currently exist in our system":
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            case "Incorrect pin, please try again":
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            case "Unfortunately the ATM doesn't have the required balance for that withdrawal" :
            case "Unfortunately the ATM doesn't have the required notes for that withdrawal" :
                return new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
            default:
                return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    // this allows a user to check their balance
    @PostMapping("/account/balance")
    public ResponseEntity<String> userBalance(@RequestBody int pin) {
        int result = accountService.userBalance(pin);
        switch (result) {
            case -1:
                return new ResponseEntity<>("Incorrect pin, please try again", HttpStatus.UNAUTHORIZED);
            case -2:
                return new ResponseEntity<>("No accounts currently exist in our system", HttpStatus.NO_CONTENT);
            default:
                return new ResponseEntity<>("Your balance is: " + result, HttpStatus.OK);
        }
    }
}
