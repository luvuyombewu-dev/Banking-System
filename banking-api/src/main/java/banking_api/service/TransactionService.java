package banking_api.service;


import banking_api.dto.TransactionResponse;
import banking_api.model.User;

import java.util.List;



public interface TransactionService {


    List<TransactionResponse> getTransactions(
            User user
    );


    TransactionResponse deposit(
            User user,
            Double amount
    );


    TransactionResponse withdraw(
            User user,
            Double amount
    );

}