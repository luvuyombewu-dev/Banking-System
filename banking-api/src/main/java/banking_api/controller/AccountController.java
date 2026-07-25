package banking_api.controller;


import banking_api.dto.AccountResponse;
import banking_api.model.User;
import banking_api.repository.UserRepository;
import banking_api.service.AccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {


    private final AccountService accountService;
    private final UserRepository userRepository;


    public AccountController(
            AccountService accountService,
            UserRepository userRepository
    ) {
        this.accountService = accountService;
        this.userRepository = userRepository;
    }



    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(
            Authentication authentication
    ) {


        String email = authentication.getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );


        return ResponseEntity.ok(
                accountService.createAccount(user)
        );
    }



    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getMyAccount(
            Authentication authentication
    ) {


        String email = authentication.getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );


        return ResponseEntity.ok(
                accountService.getMyAccount(user)
        );
    }
}