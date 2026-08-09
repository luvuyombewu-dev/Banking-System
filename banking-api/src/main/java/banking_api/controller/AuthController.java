package banking_api.controller;


import banking_api.dto.AuthResponse;
import banking_api.dto.LoginRequest;
import banking_api.dto.RegisterRequest;
import banking_api.exception.ErrorResponse;
import banking_api.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;



@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;



    public AuthController(
            AuthService authService
    ) {

        this.authService = authService;

    }




    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.ok(
                authService.register(request)
        );

    }





    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request
    ) {


        try {

            return ResponseEntity.ok(
                    authService.login(request)
            );


        } catch (BadCredentialsException ex) {


            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ErrorResponse(
                                    "Invalid credentials",
                                    401,
                                    LocalDateTime.now()
                            )
                    );


        }

    }


}