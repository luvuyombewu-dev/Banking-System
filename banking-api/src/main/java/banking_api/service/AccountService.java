package banking_api.service;

import banking_api.dto.AccountResponse;
import banking_api.model.User;

public interface AccountService {

    AccountResponse createAccount(User user);

    AccountResponse getMyAccount(User user);
}