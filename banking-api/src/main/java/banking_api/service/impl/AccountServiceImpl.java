package banking_api.service.impl;

import banking_api.dto.AccountResponse;
import banking_api.model.Account;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.service.AccountService;

import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public AccountResponse createAccount(User user) {


        Account account = new Account();


        account.setAccountNumber(
                UUID.randomUUID()
                        .toString()
                        .substring(0, 10)
        );


        account.setBalance(0.0);


        account.setUser(user);


        Account savedAccount =
                accountRepository.save(account);


        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                user.getFirstName()
                        + " "
                        + user.getLastName(),
                savedAccount.getBalance()
        );
    }


    @Override
    public AccountResponse getMyAccount(User user) {


        Account account =
                accountRepository.findByUser(user)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Account not found"
                                )
                        );


        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                user.getFirstName()
                        + " "
                        + user.getLastName(),
                account.getBalance()
        );
    }
}