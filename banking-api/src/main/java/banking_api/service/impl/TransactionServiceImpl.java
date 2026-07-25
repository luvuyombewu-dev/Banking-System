package banking_api.service.impl;


import banking_api.dto.TransactionResponse;
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
public class TransactionServiceImpl
        implements TransactionService {


    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;



    public TransactionServiceImpl(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }



    @Override
    public TransactionResponse deposit(
            User user,
            Double amount
    ) {


        Account account =
                accountRepository.findByUser(user)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Account not found"
                                )
                        );


        account.setBalance(
                account.getBalance() + amount
        );


        accountRepository.save(account);



        Transaction transaction = new Transaction();

        transaction.setAccount(account);

        transaction.setType("DEPOSIT");

        transaction.setAmount(amount);

        transaction.setDate(
                LocalDateTime.now()
        );


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
    ) {


        Account account =
                accountRepository.findByUser(user)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Account not found"
                                )
                        );



        if(account.getBalance() < amount){

            throw new RuntimeException(
                    "Insufficient balance"
            );
        }



        account.setBalance(
                account.getBalance() - amount
        );


        accountRepository.save(account);



        Transaction transaction = new Transaction();


        transaction.setAccount(account);

        transaction.setType("WITHDRAW");

        transaction.setAmount(amount);

        transaction.setDate(
                LocalDateTime.now()
        );


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
    public List<TransactionResponse> getTransactions(
            User user
    ) {


        Account account =
                accountRepository.findByUser(user)
                        .orElseThrow(
                                () -> new RuntimeException(
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
}