package ATM;
import exceptions.InsufficientFundsException;
import exceptions.InvalidTransactionException;
import java.util.List;


public interface IAccount {
    int getAccountId();
    double getBalance();
    void deposit(double amount) throws InvalidTransactionException;
    void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException;  // Указываем оба исключения
    List<Transaction> getTransactionHistory();
}