package banking_api.service.impl;


import banking_api.dto.TransactionResponse;
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
    ) {

        this.accountRepository = accountRepository;

        this.transactionRepository = transactionRepository;
    }




    @Override
    public TransactionResponse transfer(
            User sender,
            String receiverAccountNumber,
            Double amount
    ) {


        Account senderAccount =
                accountRepository.findByUser(sender)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Sender account not found"
                                )
                        );



        Account receiverAccount =
                accountRepository.findByAccountNumber(
                                receiverAccountNumber
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Receiver account not found"
                                )
                        );



        if(senderAccount.getBalance() < amount){

            throw new RuntimeException(
                    "Insufficient balance"
            );
        }



        if(senderAccount.getId()
                .equals(receiverAccount.getId())){


            throw new RuntimeException(
                    "Cannot transfer to same account"
            );
        }




        // Remove money from sender

        senderAccount.setBalance(
                senderAccount.getBalance() - amount
        );



        // Add money to receiver

        receiverAccount.setBalance(
                receiverAccount.getBalance() + amount
        );



        accountRepository.save(senderAccount);

        accountRepository.save(receiverAccount);





        // Sender transaction

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


        transactionRepository.save(
                senderTransaction
        );





        // Receiver transaction

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


        transactionRepository.save(
                receiverTransaction
        );





        return new TransactionResponse(
                senderTransaction.getId(),
                senderTransaction.getType(),
                senderTransaction.getAmount(),
                senderTransaction.getDate()
        );
    }
}