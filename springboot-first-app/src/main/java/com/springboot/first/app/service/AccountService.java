package com.springboot.first.app.service;

import com.springboot.first.app.model.ATM;
import com.springboot.first.app.model.Account;
import com.springboot.first.app.model.Withdraw;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class AccountService {
    private static final ArrayList<Account> accounts = new ArrayList<>(Arrays.asList(new Account(123456789, 1234, 800, true, 200),
            new Account(987654321, 4321, 1230, true, 150)));
    private final ATM atm = new ATM(0, 10, 30, 30, 20);

    public String withdraw(Withdraw withdraw) {
        String result = "";
        int originalFifty = atm.getFifty();
        int originalTwenty = atm.getTwenty();
        int originalTen = atm.getTen();
        int originalFive = atm.getFive();

        if (accounts.size() == 0) {
            return "No accounts currently exist in our system";
        }

        if (withdraw.getWithdrawAmount() < 0) {
            return "Please provide a valid amount to withdraw";
        }

        for (Account account : accounts) {
            if (withdraw.getAccountPin() == account.getAccountPin()) {
                Account currentUser = account;
                if (withdraw.getWithdrawAmount() <= atm.getTotalBalance()) {
                    if (withdraw.getWithdrawAmount() <= currentUser.getBalance()) {
                        if (updateATMBalance(withdraw.getWithdrawAmount())) {
                            currentUser.setBalance(currentUser.getBalance() - withdraw.getWithdrawAmount());
                            totalBalance();
                            StringBuilder stringBuilderResult = printOutOfNotes(originalFifty, originalTwenty, originalTen, originalFive);
                            result = "Your new balance is: " + currentUser.getBalance() + "\n" + stringBuilderResult;
                        } else {
                            result = "Unfortunately the ATM doesn't have the required notes for that withdrawal";
                        }
                    } else {
                        result = overdraft(currentUser, withdraw, originalFifty, originalTwenty, originalTen, originalFive);
                    }
                } else {
                    result = "Unfortunately the ATM doesn't have the required balance for that withdrawal";
                }
                break;
            } else {
                result = "Incorrect pin, please try again";
            }
        }
        return result;
    }

    public String totalBalance() {
        atm.setTotalBalance(atm.getFifty() * 50 + atm.getTwenty() * 20 + atm.getTen() * 10 + atm.getFive() * 5);
        return "The ATM has been initialised with: " + atm.getTotalBalance();
    }

    public int userBalance(int pin) {
        int result = 0;
        if (accounts.size() == 0) {
            result = 2;
        }
        for (Account account : accounts) {
            if (pin == account.getAccountPin()) {
                return account.getBalance();
            } else {
                result = 1;
            }
        }
        return result;
    }

    private String overdraft(Account currentUser, Withdraw withdraw, int originalFifty, int originalTwenty, int originalTen, int originalFive) {
        String result = "";
        int originalAmount = withdraw.getWithdrawAmount();

        if (currentUser.isOverdraftAccess()) {
            withdraw.setWithdrawAmount(withdraw.getWithdrawAmount() - currentUser.getBalance());
            currentUser.setBalance(0);
            if (withdraw.getWithdrawAmount() <= currentUser.getOverdraftBalance()) {
                if (updateATMBalance(originalAmount)) {
                    currentUser.setOverdraftBalance(currentUser.getOverdraftBalance() - withdraw.getWithdrawAmount());
                    totalBalance();
                    StringBuilder stringBuilderResult = printOutOfNotes(originalFifty, originalTwenty, originalTen, originalFive);
                    result = "Your new overdraft balance is: " + currentUser.getOverdraftBalance() + "\n" + stringBuilderResult;
                } else {
                    result = "Unfortunately the ATM doesn't have the required notes for that withdrawal";
                }
            } else {
                result = "You don't have enough in your overdraft account, your current overdraft balance is: " + currentUser.getOverdraftBalance();
            }
        } else {
            result = "You don't have enough in your current account and you don't have access to an overdraft account";
        }
        return result;
    }

    private Boolean updateATMBalance(int requestedAmount) {
        int tracker = requestedAmount;
        int fifty = tracker / 50;
        int twenty;
        int ten;
        int five;

        if (fifty >= atm.getFifty() && atm.getFifty() > 0) {
            tracker -= atm.getFifty() * 50;
            atm.setFifty(0);
        } else if (fifty < atm.getFifty()) {
            atm.setFifty(atm.getFifty() - fifty);
            tracker -= fifty * 50;
        }

        twenty = tracker / 20;

        if (twenty >= atm.getTwenty() && atm.getTwenty() > 0) {
            tracker -= atm.getTwenty() * 20;
            atm.setTwenty(0);
        } else if (twenty < atm.getTwenty() && tracker > 0) {
            atm.setTwenty(atm.getTwenty() - twenty);
            tracker -= twenty * 20;
        }

        ten = tracker / 10;

        if (ten >= atm.getTen() && atm.getTen() > 0) {
            tracker -= atm.getTen() * 10;
            atm.setTen(0);
        } else if (ten < atm.getTen() && tracker > 0) {
            atm.setTen(atm.getTen() - ten);
            tracker -= ten * 10;
        }

        five = tracker / 5;

        if (five >= atm.getFive() && atm.getFive() > 0) {
            tracker -= atm.getFive() * 5;
            atm.setFive(0);
        } else if (five < atm.getFive() && tracker > 0) {
            atm.setFive(atm.getFive() - five);
            tracker -= five * 5;
        }

        if (tracker > 0) {
            System.out.println("tracker value: " + tracker);
            return false;
        } else {
            return true;
        }
    }

    private StringBuilder printOutOfNotes(int originalFifty, int originalTwenty, int originalTen, int originalFive) {
        StringBuilder notes = new StringBuilder();
        notes.append("You are being given: \n");

        if (originalFifty - atm.getFifty() > 0) {
            notes.append(originalFifty - atm.getFifty()).append(" fifty euro note \n");
        }
        if (originalTwenty - atm.getTwenty() > 0) {
            notes.append(originalTwenty - atm.getTwenty()).append(" twenty euro note \n");
        }
        if (originalTen - atm.getTen() > 0) {
            notes.append(originalTen - atm.getTen()).append(" ten euro note \n");
        }
        if (originalFive - atm.getFive() > 0) {
            notes.append(originalFive - atm.getFive()).append(" five euro note");
        }
        return notes;
    }
}
