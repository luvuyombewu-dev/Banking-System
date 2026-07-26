package banking_api.service.impl;


import banking_api.dto.TransactionResponse;

import banking_api.exception.BadRequestException;
import banking_api.exception.ResourceNotFoundException;

import banking_api.model.Account;
import banking_api.model.Transaction;
import banking_api.model.User;

import banking_api.repository.AccountRepository;
import banking_api.repository.TransactionRepository;

import banking_api.service.TransactionService;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;



@Service
public class TransactionServiceImpl implements TransactionService {


    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;



    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository
    ){

        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;

    }






    @Override
    public List<TransactionResponse> getTransactions(
            User user
    ){


        Account account =
                accountRepository.findByUser(user)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Account not found"
                                )
                        );



        return transactionRepository
                .findByAccount(account)
                .stream()
                .map(transaction ->

                        new TransactionResponse(
                                transaction.getId(),
                                transaction.getType(),
                                transaction.getAmount(),
                                transaction.getDate()
                        )

                )
                .toList();

    }








    @Override
    public TransactionResponse deposit(
            User user,
            Double amount
    ){


        Account account =
                accountRepository.findByUser(user)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Account not found"
                                )
                        );



        if(amount <= 0){

            throw new BadRequestException(
                    "Amount must be greater than zero"
            );

        }



        account.setBalance(
                account.getBalance() + amount
        );



        accountRepository.save(account);



        Transaction transaction =
                new Transaction();



        transaction.setAccount(
                account
        );


        transaction.setType(
                "DEPOSIT"
        );


        transaction.setAmount(
                amount
        );


        transaction.setDate(
                LocalDateTime.now()
        );



        account.getTransactions()
                .add(transaction);



        Transaction saved =
                transactionRepository.save(transaction);




        return new TransactionResponse(
                saved.getId(),
                saved.getType(),
                saved.getAmount(),
                saved.getDate()
        );

    }








    @Override
    public TransactionResponse withdraw(
            User user,
            Double amount
    ){


        Account account =
                accountRepository.findByUser(user)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Account not found"
                                )
                        );



        if(amount <= 0){

            throw new BadRequestException(
                    "Amount must be greater than zero"
            );

        }



        if(account.getBalance() < amount){

            throw new BadRequestException(
                    "Insufficient balance"
            );

        }



        account.setBalance(
                account.getBalance() - amount
        );



        accountRepository.save(account);



        Transaction transaction =
                new Transaction();



        transaction.setAccount(
                account
        );


        transaction.setType(
                "WITHDRAWAL"
        );


        transaction.setAmount(
                amount
        );


        transaction.setDate(
                LocalDateTime.now()
        );



        account.getTransactions()
                .add(transaction);



        Transaction saved =
                transactionRepository.save(transaction);



        return new TransactionResponse(
                saved.getId(),
                saved.getType(),
                saved.getAmount(),
                saved.getDate()
        );

    }


}