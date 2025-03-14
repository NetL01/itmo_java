import com.lab1.exceptions.InsufficientFundsException;
import com.lab1.exceptions.InvalidTransactionException;
import com.lab1.models.Account;
import com.lab1.services.AtmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtmServiceTest {

    private AtmService atmService;
    private Account account;

    @BeforeEach
    void setUp() {
        atmService = new AtmService();
        account = atmService.createAccount("user1", "1234");
    }

    @Test
    void testCreateAccount() {
        assertNotNull(account);
        assertEquals("user1", account.getLogin());
        assertEquals(0, account.getBalance(), "Баланс должен быть 0 при создании аккаунта.");
    }

    @Test
    void testAuthenticateSuccess() {
        boolean authenticated = atmService.authenticate("user1", "1234");
        assertTrue(authenticated, "Пользователь должен быть успешно аутентифицирован.");
    }

    @Test
    void testAuthenticateFailure() {
        boolean authenticated = atmService.authenticate("user1", "wrongPin");
        assertFalse(authenticated, "Пользователь с неверным пин-кодом не должен быть аутентифицирован.");
    }

    @Test
    void testDeposit() throws InvalidTransactionException {
        atmService.deposit("user1", 100);
        assertEquals(100, account.getBalance(), "Баланс должен быть 100 после пополнения на 100.");
    }

    @Test
    void testDepositWithInvalidAmount() {
        assertThrows(InvalidTransactionException.class, () -> {
            atmService.deposit("user1", -50);
        }, "Попытка пополнить на отрицательную сумму должна выбросить исключение InvalidTransactionException.");
    }

    @Test
    void testWithdraw() throws InvalidTransactionException, InsufficientFundsException {
        atmService.deposit("user1", 100);
        atmService.withdraw("user1", 50);
        assertEquals(50, account.getBalance(), "Баланс должен быть 50 после снятия 50.");
    }

    @Test
    void testWithdrawWithInsufficientFunds() {
        assertThrows(InsufficientFundsException.class, () -> {
            atmService.withdraw("user1", 100);
        }, "Попытка снять сумму больше, чем на счету, должна выбросить исключение InsufficientFundsException.");
    }

    @Test
    void testWithdrawWithInvalidAmount() {
        assertThrows(InvalidTransactionException.class, () -> {
            atmService.withdraw("user1", -50);
        }, "Попытка снять отрицательную сумму должна выбросить исключение InvalidTransactionException.");
    }

    @Test
    void testPrintTransactionHistory() throws InvalidTransactionException {
        atmService.deposit("user1", 100);
        atmService.withdraw("user1", 50);
        assertEquals(2, account.getTransactionHistory().size(), "История транзакций должна содержать 2 транзакции.");
    }
}