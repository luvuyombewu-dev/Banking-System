package banking_api.controller;

import banking_api.dto.TransactionResponse;
import banking_api.dto.TransferRequest;
import banking_api.exception.ResourceNotFoundException;
import banking_api.model.User;
import banking_api.repository.UserRepository;
import banking_api.service.TransferService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final UserRepository userRepository;

    public TransferController(
            TransferService transferService,
            UserRepository userRepository
    ) {
        this.transferService = transferService;
        this.userRepository = userRepository;
    }

    private User getUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> transfer(
            Authentication authentication,
            @Valid @RequestBody TransferRequest request
    ) {
        return ResponseEntity.ok(
                transferService.transfer(
                        getUser(authentication),
                        request.getReceiverAccountNumber(),
                        request.getAmount()
                )
        );
    }
}