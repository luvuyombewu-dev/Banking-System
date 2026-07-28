package banking_api.repository;


import banking_api.model.Account;
import banking_api.model.Transaction;
import banking_api.model.TransactionType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {


    Page<Transaction> findByAccount(
            Account account,
            Pageable pageable
    );


    Page<Transaction> findByAccountAndType(
            Account account,
            TransactionType type,
            Pageable pageable
    );


    Page<Transaction> findByAccountAndDateBetween(
            Account account,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );


    Page<Transaction> findByAccountAndTypeAndDateBetween(
            Account account,
            TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

}