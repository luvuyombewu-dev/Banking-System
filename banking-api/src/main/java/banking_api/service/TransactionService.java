package banking_api.service;


import banking_api.dto.TransactionResponse;
import banking_api.model.TransactionType;
import banking_api.model.User;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public interface TransactionService {


    TransactionResponse deposit(
            User user,
            BigDecimal amount
    );



    TransactionResponse withdraw(
            User user,
            BigDecimal amount
    );



    TransactionResponse transfer(
            User sender,
            String receiverAccountNumber,
            BigDecimal amount
    );



    Page<TransactionResponse> getTransactions(
            User user,
            TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size
    );

}