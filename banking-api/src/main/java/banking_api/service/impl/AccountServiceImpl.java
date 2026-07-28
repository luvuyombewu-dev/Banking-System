package banking_api.service.impl;


import banking_api.model.Account;
import banking_api.model.Transaction;
import banking_api.model.TransactionType;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.repository.TransactionRepository;
import banking_api.repository.UserRepository;
import banking_api.service.AccountService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;



    @Override
    @Transactional
    public Account createAccount(String email) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        Account existingAccount =
                accountRepository.findByUser(user)
                        .orElse(null);


        if (existingAccount != null) {

            return existingAccount;

        }


        Account account = new Account();


        account.setAccountNumber(
                generateUniqueAccountNumber()
        );


        account.setAccountHolder(
                user.getFirstName()
                        + " "
                        + user.getLastName()
        );


        account.setBalance(
                BigDecimal.ZERO
        );


        account.setUser(user);

        user.setAccount(account);


        return accountRepository.save(account);

    }



    @Override
    public Account getAccountByEmail(String email) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        return accountRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Account not found")
                );

    }



    @Override
    @Transactional
    public Account deposit(
            String email,
            BigDecimal amount
    ) {


        validateAmount(amount);


        Account account = getAccountByEmail(email);


        account.setBalance(
                normalizeBalance(account.getBalance())
                        .add(amount)
        );


        account.getTransactions()
                .add(createTransaction(
                        account,
                        TransactionType.DEPOSIT,
                        amount
                ));


        return accountRepository.save(account);

    }



    @Override
    @Transactional
    public Account withdraw(
            String email,
            BigDecimal amount
    ) {


        validateAmount(amount);


        Account account = getAccountByEmail(email);


        BigDecimal currentBalance =
                normalizeBalance(account.getBalance());


        if (currentBalance.compareTo(amount) < 0) {

            throw new RuntimeException(
                    "Insufficient funds"
            );

        }


        account.setBalance(
                currentBalance.subtract(amount)
        );


        account.getTransactions()
                .add(createTransaction(
                        account,
                        TransactionType.WITHDRAW,
                        amount
                ));


        return accountRepository.save(account);

    }



    @Override
    @Transactional
    public Account transfer(
            String email,
            String receiverAccountNumber,
            BigDecimal amount
    ) {


        validateAmount(amount);


        Account sender =
                getAccountByEmail(email);


        if (sender.getBalance() == null) {

            sender.setBalance(BigDecimal.ZERO);

        }


        if (sender.getBalance().compareTo(amount) < 0) {

            throw new RuntimeException(
                    "Insufficient funds"
            );

        }


        Account receiver =
                accountRepository.findByAccountNumber(
                                receiverAccountNumber
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Receiver account not found"
                                )
                        );


        if (sender.getId().equals(receiver.getId())) {

            throw new RuntimeException(
                    "Cannot transfer to the same account"
            );

        }


        if (receiver.getBalance() == null) {

            receiver.setBalance(BigDecimal.ZERO);

        }


        sender.setBalance(
                sender.getBalance()
                        .subtract(amount)
        );


        receiver.setBalance(
                receiver.getBalance()
                        .add(amount)
        );


        sender.getTransactions()
                .add(createTransaction(
                        sender,
                        TransactionType.TRANSFER_OUT,
                        amount
                ));


        receiver.getTransactions()
                .add(createTransaction(
                        receiver,
                        TransactionType.TRANSFER_IN,
                        amount
                ));


        accountRepository.save(sender);
        accountRepository.save(receiver);


        return sender;

    }



    private Transaction createTransaction(
            Account account,
            TransactionType type,
            BigDecimal amount
    ) {


        Transaction transaction =
                new Transaction();


        transaction.setAccount(account);

        transaction.setType(type);

        transaction.setAmount(amount);

        transaction.setDate(
                LocalDateTime.now()
        );


        transactionRepository.save(transaction);

        return transaction;

    }



    private void validateAmount(BigDecimal amount) {


        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Amount must be greater than zero"
            );

        }

    }



    private BigDecimal normalizeBalance(BigDecimal balance) {

        return balance == null ? BigDecimal.ZERO : balance;

    }



    private String generateUniqueAccountNumber() {


        String accountNumber;


        do {

            accountNumber =
                    UUID.randomUUID()
                            .toString()
                            .substring(0, 8);

        } while (accountRepository.existsByAccountNumber(accountNumber));


        return accountNumber;

    }

}