package banking_api.service;

import banking_api.dto.TransactionResponse;
import banking_api.model.User;

import java.util.List;

public interface TransactionService {

    TransactionResponse deposit(User user, Double amount);

    List<TransactionResponse> getTransactions(User user);

    TransactionResponse withdraw(User user, Double amount);
}