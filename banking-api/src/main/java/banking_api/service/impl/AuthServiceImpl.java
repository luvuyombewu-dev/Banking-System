package banking_api.service.impl;


import banking_api.dto.AuthResponse;
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
    public AuthResponse register(
            RegisterRequest request
    ) {


        User user = new User();


        user.setFirstName(
                request.getFirstName()
        );


        user.setLastName(
                request.getLastName()
        );


        user.setEmail(
                request.getEmail().toLowerCase()
        );


        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        user.setRole(
                "ROLE_USER"
        );


        User savedUser =
                userRepository.save(user);



        Account account = new Account();


        account.setAccountNumber(
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
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


        User user =
                userRepository.findByEmail(
                                request.getEmail().toLowerCase()
                        )
                        .orElseThrow(
                                () -> new BadCredentialsException(
                                        "Invalid credentials"
                                )
                        );



        if(!passwordEncoder.matches(
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