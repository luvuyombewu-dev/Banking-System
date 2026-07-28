package banking_api.service.impl;


import banking_api.dto.TransactionResponse;
import banking_api.exception.BadRequestException;
import banking_api.exception.ResourceNotFoundException;
import banking_api.model.Account;
import banking_api.model.Transaction;
import banking_api.model.TransactionType;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.repository.TransactionRepository;
import banking_api.service.TransactionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class TransactionServiceImpl implements TransactionService {


    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;



    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository
    ) {

        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;

    }



    @Override
    public Page<TransactionResponse> getTransactions(
            User user,
            TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size
    ) {


        Account account =
                accountRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Account not found"
                                )
                        );


        Pageable pageable =
                PageRequest.of(page, size);



        Page<Transaction> transactions;


        if(type != null && startDate != null && endDate != null) {


            transactions =
                    transactionRepository
                            .findByAccountAndTypeAndDateBetween(
                                    account,
                                    type,
                                    startDate,
                                    endDate,
                                    pageable
                            );


        } else if(type != null) {


            transactions =
                    transactionRepository
                            .findByAccountAndType(
                                    account,
                                    type,
                                    pageable
                            );


        } else if(startDate != null && endDate != null) {


            transactions =
                    transactionRepository
                            .findByAccountAndDateBetween(
                                    account,
                                    startDate,
                                    endDate,
                                    pageable
                            );


        } else {


            transactions =
                    transactionRepository.findByAccount(
                            account,
                            pageable
                    );

        }


        return transactions.map(
                this::mapToResponse
        );

    }




    @Override
    public TransactionResponse deposit(
            User user,
            BigDecimal amount
    ) {


        Account account =
                getAccount(user);


        validateAmount(amount);


        if(account.getBalance() == null) {

            account.setBalance(BigDecimal.ZERO);

        }


        account.setBalance(
                account.getBalance()
                        .add(amount)
        );


        Transaction transaction =
                createTransaction(
                        account,
                        TransactionType.DEPOSIT,
                        amount
                );


        return mapToResponse(
                transaction
        );

    }





    @Override
    public TransactionResponse withdraw(
            User user,
            BigDecimal amount
    ) {


        Account account =
                getAccount(user);


        validateAmount(amount);


        if(account.getBalance()
                .compareTo(amount) < 0) {

            throw new BadRequestException(
                    "Insufficient balance"
            );

        }


        account.setBalance(
                account.getBalance()
                        .subtract(amount)
        );


        Transaction transaction =
                createTransaction(
                        account,
                        TransactionType.WITHDRAW,
                        amount
                );


        return mapToResponse(
                transaction
        );

    }





    @Override
    @Transactional
    public TransactionResponse transfer(
            User sender,
            String receiverAccountNumber,
            BigDecimal amount
    ) {


        validateAmount(amount);


        Account senderAccount =
                getAccount(sender);



        Account receiverAccount =
                accountRepository.findByAccountNumber(
                                receiverAccountNumber
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Receiver account not found"
                                )
                        );



        if(senderAccount.getId()
                .equals(receiverAccount.getId())) {

            throw new BadRequestException(
                    "Cannot transfer to the same account"
            );

        }



        if(senderAccount.getBalance()
                .compareTo(amount) < 0) {

            throw new BadRequestException(
                    "Insufficient balance"
            );

        }



        senderAccount.setBalance(
                senderAccount.getBalance()
                        .subtract(amount)
        );


        receiverAccount.setBalance(
                receiverAccount.getBalance()
                        .add(amount)
        );


        createTransaction(
                senderAccount,
                TransactionType.TRANSFER_OUT,
                amount
        );


        createTransaction(
                receiverAccount,
                TransactionType.TRANSFER_IN,
                amount
        );


        accountRepository.save(senderAccount);

        accountRepository.save(receiverAccount);


        return mapToResponse(
                senderAccount.getTransactions()
                        .get(senderAccount.getTransactions().size() - 1)
        );

    }





    private Account getAccount(User user) {

        return accountRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found"
                        )
                );

    }





    private void validateAmount(BigDecimal amount) {


        if(amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new BadRequestException(
                    "Amount must be greater than zero"
            );

        }

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


        account.getTransactions()
                .add(transaction);


        accountRepository.save(account);


        return transactionRepository.save(transaction);

    }





    private TransactionResponse mapToResponse(
            Transaction transaction
    ) {


        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getDate()
        );

    }

}