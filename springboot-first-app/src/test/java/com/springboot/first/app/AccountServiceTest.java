package com.springboot.first.app;

import com.springboot.first.app.model.Withdraw;
import com.springboot.first.app.service.AccountService;
import org.junit.jupiter.api.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBootConfiguration
public class AccountServiceTest {
    // You can't currently run the tests all together, they have to be run in isolation
    // 92% code coverage of the account service class
    
    @Test
    public void TestForCheckingUserBalanceWithCorrectPin() {
        AccountService accountService = new AccountService();
        accountService.totalBalance();
        assertEquals(800, accountService.userBalance(1234));
    }

    @Test
    public void TestForCheckingUserBalanceWithWrongPin() {
        AccountService accountService = new AccountService();
        accountService.totalBalance();
        assertEquals(1, accountService.userBalance(1111));
    }

    @Test
    public void TestForWithdrawingWithCorrectPinAndAmountInCurrent() {
        AccountService accountService = new AccountService();
        accountService.totalBalance();
        Withdraw withdraw = new Withdraw(30, 1234);
        assertEquals("Your new balance is: 770\n" +
                "You are being given: \n" +
                "1 twenty euro note \n" +
                "1 ten euro note \n", accountService.withdraw(withdraw));
    }

    @Test
    public void TestForWithdrawingWithCorrectPinAndAmountInOverdraft() {
        AccountService accountService = new AccountService();
        accountService.totalBalance();
        Withdraw withdraw = new Withdraw(850, 1234);
        assertEquals("Your new overdraft balance is: 150\n" +
                "You are being given: \n" +
                "10 fifty euro note \n" +
                "17 twenty euro note \n" +
                "1 ten euro note \n", accountService.withdraw(withdraw));
    }

    @Test
    public void TestForWithdrawingWithCorrectPinAndNoEnoughInOverdraft() {
        AccountService accountService = new AccountService();
        accountService.totalBalance();
        Withdraw withdraw = new Withdraw(1010, 1234);
        assertEquals("You don't have enough in your overdraft account, your current overdraft balance is: 200", accountService.withdraw(withdraw));
    }

    @Test
    public void TestForWithdrawingWithCorrectPinAndEnoughMoneyButNotEnoughNotesInTheATM() {
        AccountService accountService = new AccountService();
        accountService.totalBalance();
        Withdraw withdraw = new Withdraw(1, 1234);
        assertEquals("Unfortunately the ATM doesn't have the required notes for that withdrawal", accountService.withdraw(withdraw));
    }

    @Test
    public void TestForWithdrawingWithCorrectPinAndEnoughMoneyAndEnoughNotesButNotEnoughMoneyInTheATM() {
        AccountService accountService = new AccountService();
        accountService.totalBalance();
        Withdraw withdraw = new Withdraw(1000, 1234);
        accountService.withdraw(withdraw);
        Withdraw withdraw1 = new Withdraw(600, 4321);
        assertEquals("Unfortunately the ATM doesn't have the required balance for that withdrawal", accountService.withdraw(withdraw1));
    }

    @Test
    public void TestForWithdrawingWithWrongPin() {
        AccountService accountService = new AccountService();
        accountService.totalBalance();
        Withdraw withdraw = new Withdraw(50000, 1111);
        assertEquals("Incorrect pin, please try again", accountService.withdraw(withdraw));
    }

    @Test
    public void TestForWithdrawingWithNegativeInt() {
        AccountService accountService = new AccountService();
        Withdraw withdraw = new Withdraw(-1, 1234);
        assertEquals("Please provide a valid amount to withdraw", accountService.withdraw(withdraw));
    }
}
