package banking_api.repository;


import banking_api.model.Account;
import banking_api.model.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;



public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {


    List<Transaction> findByAccount(
            Account account
    );

}