package banking_api.service;


import banking_api.model.Account;

import java.math.BigDecimal;


public interface AccountService {


    Account createAccount(String email);


    Account getAccountByEmail(String email);


    Account deposit(
            String email,
            BigDecimal amount
    );


    Account withdraw(
            String email,
            BigDecimal amount
    );


    Account transfer(
            String email,
            String receiverAccountNumber,
            BigDecimal amount
    );

}