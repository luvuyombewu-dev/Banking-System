package banking_api.service.impl;


import banking_api.dto.AuthResponse;
import banking_api.dto.LoginRequest;
import banking_api.dto.RegisterRequest;
import banking_api.dto.UserResponse;

import banking_api.exception.BadRequestException;
import banking_api.model.User;

import banking_api.repository.UserRepository;

import banking_api.security.JwtService;

import banking_api.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;



    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }





    @Override
    public UserResponse register(
            RegisterRequest request
    ) {


        if(userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new BadRequestException(
                    "Email already exists"
            );

        }



        User user = new User();


        user.setFirstName(
                request.getFirstName()
        );


        user.setLastName(
                request.getLastName()
        );


        user.setEmail(
                request.getEmail()
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



        return new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                null
        );

    }








    @Override
    public AuthResponse login(
            LoginRequest request
    ) {


        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(
                                () -> new BadRequestException(
                                        "Invalid email or password"
                                )
                        );



        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {


            throw new BadRequestException(
                    "Invalid email or password"
            );

        }



        String token =
                jwtService.generateToken(
                        user.getEmail()
                );



        return new AuthResponse(
                token
        );

    }

}