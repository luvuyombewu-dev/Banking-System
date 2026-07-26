package banking_api.service;

import banking_api.model.Account;

public interface AccountService {

    Account createAccount(String email);

    Account getAccountByEmail(String email);

}