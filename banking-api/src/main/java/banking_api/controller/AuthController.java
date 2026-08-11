package banking_api.controller;

import banking_api.dto.AuthResponse;
import banking_api.dto.ChangePasswordRequest;
import banking_api.dto.ForgotPasswordRequest;
import banking_api.dto.LoginRequest;
import banking_api.dto.RegisterRequest;
import banking_api.dto.ResetPasswordRequest;
import banking_api.exception.ErrorResponse;
import banking_api.service.AuthService;
import banking_api.service.PasswordResetService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final PasswordResetService passwordResetService;


    public AuthController(
            AuthService authService,
            PasswordResetService passwordResetService
    ) {

        this.authService = authService;

        this.passwordResetService = passwordResetService;

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


    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        /*
         * The endpoint requires an authenticated user.
         * If the JWT authentication was not established,
         * return 401 instead of allowing a NullPointerException
         * to become a 500 server error.
         */
        if (
                authentication == null
                        || !authentication.isAuthenticated()
                        || authentication.getName() == null
                        || authentication.getName().isBlank()
        ) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ErrorResponse(
                                    "Authentication required",
                                    401,
                                    LocalDateTime.now()
                            )
                    );

        }


        try {

            authService.changePassword(
                    authentication.getName(),
                    request
            );


            return ResponseEntity.ok(
                    "Password changed successfully"
            );


        } catch (BadCredentialsException ex) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ErrorResponse(
                                    ex.getMessage(),
                                    401,
                                    LocalDateTime.now()
                            )
                    );


        } catch (IllegalArgumentException ex) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ErrorResponse(
                                    ex.getMessage(),
                                    400,
                                    LocalDateTime.now()
                            )
                    );

        }

    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {

        passwordResetService.forgotPassword(
                request.getEmail()
        );


        return ResponseEntity.ok(
                "Password reset request processed"
        );

    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {

        passwordResetService.resetPassword(
                request.getToken(),
                request.getPassword()
        );


        return ResponseEntity.ok(
                "Password reset successfully"
        );

    }

}