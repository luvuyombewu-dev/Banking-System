package banking_api.service.impl;

import banking_api.model.Account;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.repository.UserRepository;
import banking_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    @Override
    public Account createAccount(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        Account account = new Account();

        account.setAccountNumber(
                UUID.randomUUID().toString().substring(0, 8)
        );

        account.setAccountHolder(
                user.getFirstName() + " " + user.getLastName()
        );

        account.setBalance(0.0);

        account.setUser(user);


        return accountRepository.save(account);
    }


    @Override
    public Account getAccountByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        return accountRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));
    }
}