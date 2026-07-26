package banking_api.controller;


import banking_api.dto.TransactionResponse;
import banking_api.dto.TransferRequest;
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



    @PostMapping
    public ResponseEntity<TransactionResponse> transfer(
            Authentication authentication,
            @RequestBody @Valid TransferRequest request
    ) {


        String email = authentication.getName();


        User sender =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );


        return ResponseEntity.ok(
                transferService.transfer(
                        sender,
                        request.getReceiverAccountNumber(),
                        request.getAmount()
                )
        );
    }

}