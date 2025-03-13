package ATM;
import exceptions.InsufficientFundsException;
import exceptions.InvalidTransactionException;
import java.util.HashMap;
import java.util.Map;


public class ATM {
    private Map<String, Account> accounts = new HashMap<>();
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

    public void withdraw(String login, double amount) throws InvalidTransactionException, InsufficientFundsException {
        Account account = getAccount(login);
        if (account != null) {
            account.withdraw(amount);
        }
    }

    public void printTransactionHistory(String login) {
        Account account = getAccount(login);
        if (account != null) {
            account.getTransactionHistory().forEach(transaction ->
                    System.out.println(transaction)
            );
        }
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
}