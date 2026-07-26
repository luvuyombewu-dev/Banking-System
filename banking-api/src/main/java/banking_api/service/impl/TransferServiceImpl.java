package banking_api.service.impl;


import banking_api.dto.TransactionResponse;

import banking_api.exception.BadRequestException;
import banking_api.exception.ResourceNotFoundException;

import banking_api.model.Account;
import banking_api.model.Transaction;
import banking_api.model.User;

import banking_api.repository.AccountRepository;
import banking_api.repository.TransactionRepository;

import banking_api.service.TransferService;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;



@Service
public class TransferServiceImpl implements TransferService {



    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;




    public TransferServiceImpl(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ){

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;

    }







    @Override
    public TransactionResponse transfer(
            User sender,
            String receiverAccountNumber,
            Double amount
    ){



        Account senderAccount =
                accountRepository.findByUser(sender)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Sender account not found"
                                )
                        );




        Account receiverAccount =
                accountRepository.findByAccountNumber(
                                receiverAccountNumber
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Receiver account not found"
                                )
                        );





        if(amount <= 0){

            throw new BadRequestException(
                    "Amount must be greater than zero"
            );

        }




        if(senderAccount.getBalance() < amount){

            throw new BadRequestException(
                    "Insufficient balance"
            );

        }




        if(senderAccount.getId()
                .equals(receiverAccount.getId())){


            throw new BadRequestException(
                    "Cannot transfer to same account"
            );

        }





        senderAccount.setBalance(
                senderAccount.getBalance() - amount
        );



        receiverAccount.setBalance(
                receiverAccount.getBalance() + amount
        );







        Transaction senderTransaction =
                new Transaction();



        senderTransaction.setAccount(
                senderAccount
        );


        senderTransaction.setType(
                "TRANSFER_OUT"
        );


        senderTransaction.setAmount(
                amount
        );


        senderTransaction.setDate(
                LocalDateTime.now()
        );



        senderAccount.getTransactions()
                .add(senderTransaction);




        transactionRepository.save(
                senderTransaction
        );








        Transaction receiverTransaction =
                new Transaction();



        receiverTransaction.setAccount(
                receiverAccount
        );


        receiverTransaction.setType(
                "TRANSFER_IN"
        );


        receiverTransaction.setAmount(
                amount
        );


        receiverTransaction.setDate(
                LocalDateTime.now()
        );



        receiverAccount.getTransactions()
                .add(receiverTransaction);




        transactionRepository.save(
                receiverTransaction
        );





        accountRepository.save(senderAccount);

        accountRepository.save(receiverAccount);







        return new TransactionResponse(
                senderTransaction.getId(),
                senderTransaction.getType(),
                senderTransaction.getAmount(),
                senderTransaction.getDate()
        );

    }


}