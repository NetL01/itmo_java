package com.lab1;

import com.lab1.exceptions.InsufficientFundsException;
import com.lab1.exceptions.InvalidTransactionException;
import com.lab1.models.Account;
import com.lab1.services.AtmService;
import com.lab1.services.IAtmService;

import java.util.Scanner;


public class AtmApp {
    private static final IAtmService atm = new AtmService();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account account = null;
        String enteredLogin = null;
        String enteredPinCode = null;

        System.out.println("Добро пожаловать в банкомат!");

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Создать новый счет");
            System.out.println("2. Войти в существующий счет");
            System.out.println("3. Выход");
            System.out.print("Выберите опцию: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Введите логин для нового счета: ");
                    String login = scanner.next();
                    System.out.print("Введите пин-код для нового счета: ");
                    String pinCode = scanner.next();
                    account = atm.createAccount(login, pinCode);
                    enteredLogin = login;
                    enteredPinCode = enteredPinCode;
                    System.out.println("Новый счет создан! Логин: " + login + ". Баланс: 0.0");
                    break;
                case 2:
                    System.out.print("Введите логин: ");
                    enteredLogin = scanner.next();
                    System.out.print("Введите пин-код: ");
                    enteredPinCode = scanner.next();

                    boolean authenticated = atm.authenticate(enteredLogin, enteredPinCode);
                    if (authenticated) {
                        account = atm.getAccount(enteredLogin);
                        System.out.println("Вы вошли в счет с логином: " + enteredLogin);
                    } else {
                        System.out.println("Неверный логин или пин-код.");
                    }
                    break;
                case 3:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    continue;
            }

            if (account != null) {
                while (true) {
                    System.out.println("\nМеню операций с счетом:");
                    System.out.println("1. Посмотреть баланс");
                    System.out.println("2. Пополнить счет");
                    System.out.println("3. Снять со счета");
                    System.out.println("4. История транзакций");
                    System.out.println("5. Выйти из счета");

                    System.out.print("Выберите операцию: ");
                    int operation = scanner.nextInt();

                    switch (operation) {
                        case 1:
                            System.out.println("Баланс счета: " + account.getBalance());
                            break;
                        case 2:
                            System.out.print("Введите сумму для пополнения: ");
                            double depositAmount = scanner.nextDouble();
                            try {
                                atm.deposit(enteredLogin, depositAmount);
                                System.out.println("Счет пополнен на сумму: " + depositAmount);
                            } catch (InvalidTransactionException e) {
                                System.out.println("Ошибка: " + e.getMessage());
                            }
                            break;
                        case 3:
                            System.out.print("Введите сумму для снятия: ");
                            double withdrawAmount = scanner.nextDouble();
                            try {
                                atm.withdraw(enteredLogin, withdrawAmount);
                                System.out.println("Снято со счета: " + withdrawAmount);
                            } catch (InsufficientFundsException | InvalidTransactionException e) {
                                System.out.println("Ошибка: " + e.getMessage());
                            }
                            break;
                        case 4:
                            atm.printTransactionHistory(enteredLogin);
                            break;
                        case 5:
                            System.out.println("Выход из счета.");
                            account = null;
                            enteredLogin = null;
                            enteredPinCode = null;
                            break;
                        default:
                            System.out.println("Некорректный выбор. Попробуйте снова.");
                            break;
                    }

                    if (account == null) break;
                }
            }
        }
    }
}