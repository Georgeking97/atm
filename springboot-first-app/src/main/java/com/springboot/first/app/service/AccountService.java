package com.springboot.first.app.service;

import com.springboot.first.app.model.ATM;
import com.springboot.first.app.model.Account;
import com.springboot.first.app.model.Withdraw;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AccountService {
    private final ATM atm = new ATM(0, 10, 30, 30, 20);
    private final HashMap<Integer, Account> accounts = new HashMap<Integer, Account>() {{
        accounts.put(1234, new Account(123456789, 1234, 800, true, 200));
        accounts.put(4321, new Account(987654321, 4321, 1230, true, 150));
    }};

    public String withdraw(Withdraw withdraw) {
        int originalFifty = atm.getFifty();
        int originalTwenty = atm.getTwenty();
        int originalTen = atm.getTen();
        int originalFive = atm.getFive();

        if (withdraw.getWithdrawAmount() < 0) {
            return "Please provide a valid amount to withdraw";
        }

        if (accounts.size() == 0) {
            return "No accounts currently exist in our system";
        }

        if (!accounts.containsKey(withdraw.getAccountPin())) {
            return "Incorrect pin, please try again";
        }

        if (!(withdraw.getWithdrawAmount() <= atm.getTotalBalance())) {
            return "Unfortunately the ATM doesn't have the required balance for that withdrawal";
        }

        Account currentUser = accounts.get(withdraw.getAccountPin());

        if (withdraw.getWithdrawAmount() > currentUser.getBalance()) {
            return overdraft(currentUser, withdraw, originalFifty, originalTwenty, originalTen, originalFive);
        }

        if (!updateATMBalance(withdraw.getWithdrawAmount())) {
            return "Unfortunately the ATM doesn't have the required notes for that withdrawal";
        }

        currentUser.setBalance(currentUser.getBalance() - withdraw.getWithdrawAmount());
        totalBalance();
        StringBuilder stringBuilderResult = printOutOfNotes(originalFifty, originalTwenty, originalTen, originalFive);
        return  "Your new balance is: " + currentUser.getBalance() + "\n" + stringBuilderResult;
    }

    public String totalBalance() {
        atm.setTotalBalance(atm.getFifty() * 50 + atm.getTwenty() * 20 + atm.getTen() * 10 + atm.getFive() * 5);
        return "The ATM has been initialised with: " + atm.getTotalBalance();
    }

    public int userBalance(int pin) {
        if (accounts.size() == 0) {
            return -2;
        }

        if (accounts.containsKey(pin)) {
            return accounts.get(pin).getBalance();
        } else {
            return -1;
        }
    }

    private String overdraft(Account currentUser, Withdraw withdraw, int originalFifty, int originalTwenty, int originalTen, int originalFive) {
        int originalAmount = withdraw.getWithdrawAmount();

        if (!currentUser.isOverdraftAccess()) {
            return "You don't have enough in your current account and you don't have access to an overdraft account";
        }

        withdraw.setWithdrawAmount(withdraw.getWithdrawAmount() - currentUser.getBalance());

        if (withdraw.getWithdrawAmount() > currentUser.getOverdraftBalance()) {
            return "You don't have enough in your overdraft account, your current overdraft balance is: " + currentUser.getOverdraftBalance();
        }

        if (!updateATMBalance(originalAmount)) {
            return "Unfortunately the ATM doesn't have the required notes for that withdrawal";
        }

        currentUser.setBalance(0);
        currentUser.setOverdraftBalance(currentUser.getOverdraftBalance() - withdraw.getWithdrawAmount());
        totalBalance();
        StringBuilder stringBuilderResult = printOutOfNotes(originalFifty, originalTwenty, originalTen, originalFive);
        return "Your new overdraft balance is: " + currentUser.getOverdraftBalance() + "\n" + stringBuilderResult;
    }

    private Boolean updateATMBalance(int tracker) {
        int fifty = tracker / 50;
        int twenty;
        int ten;
        int five;
        int originalFifty = atm.getFifty();
        int originalTwenty = atm.getTwenty();
        int originalTen = atm.getTen();
        int originalFive = atm.getFive();

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
            atm.setFifty(originalFifty);
            atm.setTwenty(originalTwenty);
            atm.setTen(originalTen);
            atm.setFive(originalFive);
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
