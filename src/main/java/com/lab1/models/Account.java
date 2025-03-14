package com.lab1.models;

import com.lab1.exceptions.InsufficientFundsException;
import com.lab1.exceptions.InvalidTransactionException;

import java.util.ArrayList;
import java.util.List;


public class Account {
    private final String login;
    private final String pinCode;
    private double balance;
    private final List<Transaction> transactions;

    public Account(String login, String pinCode) {
        this.login = login;
        this.pinCode = pinCode;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException("Сумма пополнения должна быть положительной.");
        }
        balance += amount;
        transactions.add(new Transaction("Пополнение", amount));
    }

    public void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException("Сумма для снятия должна быть положительной.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Недостаточно средств на счете.");
        }
        balance -= amount;
        transactions.add(new Transaction("Снятие", -amount));
    }

    public List<Transaction> getTransactionHistory() {
        return transactions;
    }

    public boolean authenticate(String login, String pinCode) {
        return this.login.equals(login) && this.pinCode.equals(pinCode);
    }
}