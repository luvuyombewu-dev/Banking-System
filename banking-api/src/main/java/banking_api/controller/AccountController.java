package banking_api.controller;

import banking_api.model.Account;
import banking_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping("/create")
    public ResponseEntity<?> createAccount(Authentication authentication) {

        String email = authentication.getName();

        Account account = accountService.createAccount(email);

        return ResponseEntity.ok(account);
    }


    @GetMapping("/my-account")
    public ResponseEntity<?> getMyAccount(Authentication authentication) {

        String email = authentication.getName();

        Account account = accountService.getAccountByEmail(email);

        return ResponseEntity.ok(account);
    }
}