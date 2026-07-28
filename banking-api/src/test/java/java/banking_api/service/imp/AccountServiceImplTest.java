package banking_api.service.impl;

import banking_api.model.Account;
import banking_api.model.Transaction;
import banking_api.model.TransactionType;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.repository.TransactionRepository;
import banking_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_shouldCreateNewAccount_whenUserHasNoAccount() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Luvuyo");
        user.setLastName("Mbewu");
        user.setEmail("luvuyo@test.com");

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(user));
        when(accountRepository.findByUser(user)).thenReturn(Optional.empty());
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account created = accountService.createAccount("luvuyo@test.com");

        assertNotNull(created);
        assertEquals("Luvuyo Mbewu", created.getAccountHolder());
        assertEquals(BigDecimal.ZERO, created.getBalance());
        assertEquals(user, created.getUser());
        assertNotNull(created.getAccountNumber());
        assertEquals(8, created.getAccountNumber().length());

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void getAccountByEmail_shouldReturnAccount_whenAccountExists() {
        User user = new User();
        user.setEmail("luvuyo@test.com");

        Account account = new Account();
        account.setId(8L);
        account.setAccountNumber("b6b6ec1b");
        account.setBalance(new BigDecimal("500.00"));
        account.setAccountHolder("Luvuyo Mbewu");
        account.setUser(user);

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(user));
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountByEmail("luvuyo@test.com");

        assertEquals(account, result);
        assertEquals(new BigDecimal("500.00"), result.getBalance());
    }

    @Test
    void deposit_shouldIncreaseBalanceAndSaveTransaction() {
        User user = new User();
        user.setEmail("luvuyo@test.com");

        Account account = new Account();
        account.setId(8L);
        account.setBalance(new BigDecimal("500.00"));
        account.setUser(user);

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(user));
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.deposit("luvuyo@test.com", new BigDecimal("200.00"));

        assertEquals(new BigDecimal("700.00"), result.getBalance());
        assertEquals(1, result.getTransactions().size());
        assertEquals(TransactionType.DEPOSIT, result.getTransactions().get(0).getType());
        assertEquals(new BigDecimal("200.00"), result.getTransactions().get(0).getAmount());

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).save(captor.capture());
        assertEquals(TransactionType.DEPOSIT, captor.getValue().getType());
        assertEquals(new BigDecimal("200.00"), captor.getValue().getAmount());
    }

    @Test
    void withdraw_shouldDecreaseBalanceAndSaveTransaction() {
        User user = new User();
        user.setEmail("luvuyo@test.com");

        Account account = new Account();
        account.setId(8L);
        account.setBalance(new BigDecimal("700.00"));
        account.setUser(user);

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(user));
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.withdraw("luvuyo@test.com", new BigDecimal("300.00"));

        assertEquals(new BigDecimal("400.00"), result.getBalance());
        assertEquals(1, result.getTransactions().size());
        assertEquals(TransactionType.WITHDRAW, result.getTransactions().get(0).getType());
        assertEquals(new BigDecimal("300.00"), result.getTransactions().get(0).getAmount());
    }

    @Test
    void withdraw_shouldThrow_whenInsufficientFunds() {
        User user = new User();
        user.setEmail("luvuyo@test.com");

        Account account = new Account();
        account.setBalance(new BigDecimal("100.00"));
        account.setUser(user);

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(user));
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> accountService.withdraw("luvuyo@test.com", new BigDecimal("300.00"))
        );

        assertEquals("Insufficient funds", ex.getMessage());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void transfer_shouldMoveMoneyBetweenAccountsAndSaveTransactions() {
        User senderUser = new User();
        senderUser.setEmail("luvuyo@test.com");

        Account sender = new Account();
        sender.setId(8L);
        sender.setBalance(new BigDecimal("500.00"));
        sender.setUser(senderUser);

        Account receiver = new Account();
        receiver.setId(9L);
        receiver.setAccountNumber("b1d90cea");
        receiver.setBalance(new BigDecimal("200.00"));

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(senderUser));
        when(accountRepository.findByUser(senderUser)).thenReturn(Optional.of(sender));
        when(accountRepository.findByAccountNumber("b1d90cea")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.transfer("luvuyo@test.com", "b1d90cea", new BigDecimal("200.00"));

        assertEquals(new BigDecimal("300.00"), sender.getBalance());
        assertEquals(new BigDecimal("400.00"), receiver.getBalance());
        assertEquals(new BigDecimal("300.00"), result.getBalance());

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(2)).save(captor.capture());

        assertEquals(TransactionType.TRANSFER_OUT, captor.getAllValues().get(0).getType());
        assertEquals(TransactionType.TRANSFER_IN, captor.getAllValues().get(1).getType());
    }

    @Test
    void transfer_shouldThrow_whenReceiverIsSameAsSender() {
        User user = new User();
        user.setEmail("luvuyo@test.com");

        Account sender = new Account();
        sender.setId(8L);
        sender.setBalance(new BigDecimal("500.00"));
        sender.setUser(user);
        sender.setAccountNumber("b6b6ec1b");

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(user));
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(sender));
        when(accountRepository.findByAccountNumber("b6b6ec1b")).thenReturn(Optional.of(sender));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> accountService.transfer("luvuyo@test.com", "b6b6ec1b", new BigDecimal("200.00"))
        );

        assertEquals("Cannot transfer to the same account", ex.getMessage());
    }
}