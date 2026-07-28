package banking_api.controller;

import banking_api.dto.DepositRequest;
import banking_api.dto.TransferRequest;
import banking_api.dto.WithdrawRequest;
import banking_api.model.Account;
import banking_api.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(Authentication authentication) {

        return ResponseEntity.ok(
                accountService.createAccount(authentication.getName())
        );
    }

    @GetMapping("/my-account")
    public ResponseEntity<?> getMyAccount(Authentication authentication) {

        return ResponseEntity.ok(
                accountService.getAccountByEmail(authentication.getName())
        );
    }

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(
            Authentication authentication,
            @Valid @RequestBody DepositRequest request) {

        return ResponseEntity.ok(
                accountService.deposit(
                        authentication.getName(),
                        request.getAmount()
                )
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(
            Authentication authentication,
            @Valid @RequestBody WithdrawRequest request) {

        return ResponseEntity.ok(
                accountService.withdraw(
                        authentication.getName(),
                        request.getAmount()
                )
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<Account> transfer(
            Authentication authentication,
            @Valid @RequestBody TransferRequest request) {

        return ResponseEntity.ok(
                accountService.transfer(
                        authentication.getName(),
                        request.getReceiverAccountNumber(),
                        request.getAmount()
                )
        );
    }
}