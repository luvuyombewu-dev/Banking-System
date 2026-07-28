package banking_api.service.impl;

import banking_api.dto.TransactionResponse;
import banking_api.model.Account;
import banking_api.model.Transaction;
import banking_api.model.TransactionType;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void deposit_shouldIncreaseBalanceAndReturnTransactionResponse() {
        User user = new User();
        user.setEmail("luvuyo@test.com");

        Account account = new Account();
        account.setId(8L);
        account.setBalance(new BigDecimal("500.00"));
        account.setUser(user);

        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponse response = transactionService.deposit(user, new BigDecimal("200.00"));

        assertEquals(TransactionType.DEPOSIT, response.getType());
        assertEquals(new BigDecimal("200.00"), response.getAmount());
        assertEquals(new BigDecimal("700.00"), account.getBalance());
    }

    @Test
    void withdraw_shouldDecreaseBalanceAndReturnTransactionResponse() {
        User user = new User();
        user.setEmail("luvuyo@test.com");

        Account account = new Account();
        account.setId(8L);
        account.setBalance(new BigDecimal("700.00"));
        account.setUser(user);

        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponse response = transactionService.withdraw(user, new BigDecimal("300.00"));

        assertEquals(TransactionType.WITHDRAW, response.getType());
        assertEquals(new BigDecimal("300.00"), response.getAmount());
        assertEquals(new BigDecimal("400.00"), account.getBalance());
    }

    @Test
    void transfer_shouldMoveMoneyAndReturnSenderTransactionResponse() {
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

        when(accountRepository.findByUser(senderUser)).thenReturn(Optional.of(sender));
        when(accountRepository.findByAccountNumber("b1d90cea")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponse response = transactionService.transfer(senderUser, "b1d90cea", new BigDecimal("200.00"));

        assertEquals(TransactionType.TRANSFER_OUT, response.getType());
        assertEquals(new BigDecimal("200.00"), response.getAmount());
        assertEquals(new BigDecimal("300.00"), sender.getBalance());
        assertEquals(new BigDecimal("400.00"), receiver.getBalance());

        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void getTransactions_shouldReturnFilteredPageByType() {
        User user = new User();
        user.setEmail("luvuyo@test.com");

        Account account = new Account();
        account.setId(8L);
        account.setUser(user);

        Transaction tx = new Transaction();
        tx.setId(21L);
        tx.setType(TransactionType.DEPOSIT);
        tx.setAmount(new BigDecimal("1000.00"));
        tx.setDate(LocalDateTime.now());
        tx.setAccount(account);

        Page<Transaction> page = new PageImpl<>(List.of(tx));

        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
        when(transactionRepository.findByAccountAndType(eq(account), eq(TransactionType.DEPOSIT), any()))
                .thenReturn(page);

        Page<TransactionResponse> result = transactionService.getTransactions(
                user,
                TransactionType.DEPOSIT,
                null,
                null,
                0,
                10
        );

        assertEquals(1, result.getTotalElements());
        assertEquals(TransactionType.DEPOSIT, result.getContent().get(0).getType());
        assertEquals(new BigDecimal("1000.00"), result.getContent().get(0).getAmount());

        verify(transactionRepository, times(1))
                .findByAccountAndType(eq(account), eq(TransactionType.DEPOSIT), eq(PageRequest.of(0, 10)));
    }
}