package banking_api.controller;


import banking_api.dto.DepositRequest;
import banking_api.dto.TransactionResponse;
import banking_api.dto.TransferRequest;
import banking_api.dto.WithdrawRequest;
import banking_api.model.TransactionType;
import banking_api.model.User;
import banking_api.repository.UserRepository;
import banking_api.service.TransactionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;



@RestController
@RequestMapping("/api/transactions")
@SecurityRequirement(name = "bearerAuth")
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



    private User getUser(
            Authentication authentication
    ) {

        return userRepository.findByEmail(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

    }



    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @Valid @RequestBody DepositRequest request,
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
            @Valid @RequestBody WithdrawRequest request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                transactionService.withdraw(
                        getUser(authentication),
                        request.getAmount()
                )
        );

    }



    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(
            @Valid @RequestBody TransferRequest request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                transactionService.transfer(
                        getUser(authentication),
                        request.getReceiverAccountNumber(),
                        request.getAmount()
                )
        );

    }




    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getTransactions(
            Authentication authentication,

            @RequestParam(required = false)
            TransactionType type,


            @RequestParam(required = false)
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime startDate,


            @RequestParam(required = false)
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime endDate,


            @RequestParam(defaultValue = "0")
            int page,


            @RequestParam(defaultValue = "10")
            int size

    ) {


        return ResponseEntity.ok(
                transactionService.getTransactions(
                        getUser(authentication),
                        type,
                        startDate,
                        endDate,
                        page,
                        size
                )
        );

    }

}