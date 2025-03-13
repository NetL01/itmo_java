package com.lab1.services;

import com.lab1.exceptions.InvalidTransactionException;
import com.lab1.models.Account;

import java.util.HashMap;
import java.util.Map;

public class AtmService implements IAtmService {
    private final Map<String, Account> accounts = new HashMap<>();
    private Account currentAccount = null;

    public Account createAccount(String login, String pinCode) {
        Account newAccount = new Account(login, pinCode);
        accounts.put(login, newAccount);
        return newAccount;
    }


    public Account getAccount(String login) {
        return accounts.get(login);
    }

    public boolean authenticate(String login, String pinCode) {
        Account account = getAccount(login);
        if (account != null && account.authenticate(login, pinCode)) {
            currentAccount = account;
            return true;
        }
        return false;
    }

    public void deposit(String login, double amount) throws InvalidTransactionException {
        Account account = getAccount(login);
        if (account != null) {
            account.deposit(amount);
        }
    }

    public void withdraw(String login, double amount) throws InvalidTransactionException {
        Account account = getAccount(login);
        if (account != null) {
            account.withdraw(amount);
        }
    }

    public void printTransactionHistory(String login) {
        Account account = getAccount(login);
        if (account != null) {
            account.getTransactionHistory().forEach(System.out::println);
        }
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
}
