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
import banking_api.service.TransferService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class TransferServiceImpl implements TransferService {


    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;



    public TransferServiceImpl(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;

    }



    @Override
    @Transactional
    public TransactionResponse transfer(
            User sender,
            String receiverAccountNumber,
            BigDecimal amount
    ) {


        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new BadRequestException(
                    "Amount must be greater than zero"
            );

        }



        Account senderAccount =
                accountRepository.findByUser(sender)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Sender account not found"
                                )
                        );



        Account receiverAccount =
                accountRepository.findByAccountNumber(
                                receiverAccountNumber
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Receiver account not found"
                                )
                        );



        if (senderAccount.getId()
                .equals(receiverAccount.getId())) {

            throw new BadRequestException(
                    "Cannot transfer to same account"
            );

        }



        if (senderAccount.getBalance() == null) {

            senderAccount.setBalance(
                    BigDecimal.ZERO
            );

        }



        if (receiverAccount.getBalance() == null) {

            receiverAccount.setBalance(
                    BigDecimal.ZERO
            );

        }



        if (senderAccount.getBalance()
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



        Transaction senderTransaction =
                createTransaction(
                        senderAccount,
                        TransactionType.TRANSFER_OUT,
                        amount
                );



        Transaction receiverTransaction =
                createTransaction(
                        receiverAccount,
                        TransactionType.TRANSFER_IN,
                        amount
                );



        senderAccount.getTransactions()
                .add(senderTransaction);


        receiverAccount.getTransactions()
                .add(receiverTransaction);



        accountRepository.save(senderAccount);

        accountRepository.save(receiverAccount);



        Transaction saved =
                transactionRepository.save(
                        senderTransaction
                );



        transactionRepository.save(
                receiverTransaction
        );



        return new TransactionResponse(
                saved.getId(),
                saved.getType(),
                saved.getAmount(),
                saved.getDate()
        );

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


        return transaction;

    }

}