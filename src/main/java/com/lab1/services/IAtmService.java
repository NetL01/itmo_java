package com.lab1.services;

import com.lab1.models.Account;
import com.lab1.exceptions.InsufficientFundsException;
import com.lab1.exceptions.InvalidTransactionException;

public interface IAtmService {
    Account createAccount(String login, String pinCode);

    Account getAccount(String login);

    boolean authenticate(String login, String pinCode);

    void deposit(String login, double amount) throws InvalidTransactionException;

    void withdraw(String login, double amount) throws InvalidTransactionException, InsufficientFundsException;

    void printTransactionHistory(String login);

    Account getCurrentAccount();
}
