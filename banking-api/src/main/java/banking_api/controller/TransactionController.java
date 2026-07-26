package banking_api.controller;

import banking_api.dto.DepositRequest;
import banking_api.dto.TransactionResponse;
import banking_api.dto.WithdrawRequest;
import banking_api.model.User;
import banking_api.repository.UserRepository;
import banking_api.service.TransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;

    public TransactionController(
            TransactionService transactionService,
            UserRepository userRepository
    ) {
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }

    private User getUser(Authentication authentication) {

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @RequestBody DepositRequest request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                transactionService.deposit(
                        getUser(authentication),
                        request.getAmount()
                )
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(
            @RequestBody WithdrawRequest request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                transactionService.withdraw(
                        getUser(authentication),
                        request.getAmount()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                transactionService.getTransactions(
                        getUser(authentication)
                )
        );
    }
}