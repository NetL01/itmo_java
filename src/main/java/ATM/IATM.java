package ATM;
import exceptions.InsufficientFundsException;
import exceptions.InvalidTransactionException;


public interface IATM {
    Account createAccount(double initialBalance);
    Account getAccount(int accountId);
    double checkBalance(int accountId);
    void deposit(int accountId, double amount) throws InvalidTransactionException;
    void withdraw(int accountId, double amount) throws InsufficientFundsException, InvalidTransactionException;
    void printTransactionHistory(int accountId);
}