package banking_api.service.impl;

import banking_api.dto.AuthResponse;
import banking_api.dto.ChangePasswordRequest;
import banking_api.dto.LoginRequest;
import banking_api.dto.RegisterRequest;
import banking_api.model.Account;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.repository.UserRepository;
import banking_api.security.JwtService;
import banking_api.service.AuthService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public AuthResponse register(
            RegisterRequest request
    ) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (userRepository.existsByEmail(email)) {

            throw new IllegalArgumentException(
                    "An account with this email already exists."
            );
        }

        User user = new User();

        user.setFirstName(
                request.getFirstName().trim()
        );

        user.setLastName(
                request.getLastName().trim()
        );

        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole("ROLE_USER");

        User savedUser =
                userRepository.save(user);

        Account account = new Account();

        account.setAccountNumber(
                generateAccountNumber()
        );

        account.setAccountHolder(
                savedUser.getFirstName()
                        + " "
                        + savedUser.getLastName()
        );

        account.setBalance(
                BigDecimal.ZERO
        );

        account.setUser(savedUser);

        Account savedAccount =
                accountRepository.save(account);

        savedUser.setAccount(savedAccount);

        String token =
                jwtService.generateToken(
                        savedUser.getEmail()
                );

        return buildAuthResponse(
                token,
                savedUser
        );
    }

    @Override
    public AuthResponse login(
            LoginRequest request
    ) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new BadCredentialsException(
                                        "Invalid credentials"
                                )
                        );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new BadCredentialsException(
                    "Invalid credentials"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return buildAuthResponse(
                token,
                user
        );
    }

    @Override
    @Transactional
    public void changePassword(
            String email,
            ChangePasswordRequest request
    ) {

        User user =
                userRepository.findByEmail(
                        email.toLowerCase()
                )
                .orElseThrow(
                        () -> new BadCredentialsException(
                                "Authenticated user not found"
                        )
                );

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword()
        )) {

            throw new BadCredentialsException(
                    "Current password is incorrect"
            );
        }

        if (!request.getNewPassword().equals(
                request.getConfirmPassword()
        )) {

            throw new IllegalArgumentException(
                    "New passwords do not match"
            );
        }

        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword()
        )) {

            throw new IllegalArgumentException(
                    "New password must be different from current password"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {

            accountNumber =
                    String.format(
                            "%08d",
                            UUID.randomUUID()
                                    .hashCode()
                                    & 0x7fffffff
                    )
                    .substring(0, 8);

        } while (
                accountRepository.existsByAccountNumber(
                        accountNumber
                )
        );

        return accountNumber;
    }

    private AuthResponse buildAuthResponse(
            String token,
            User user
    ) {

        return new AuthResponse(
                token,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}