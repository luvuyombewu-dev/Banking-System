package banking_api.service;

import banking_api.dto.AuthResponse;
import banking_api.dto.ChangePasswordRequest;
import banking_api.dto.LoginRequest;
import banking_api.dto.RegisterRequest;


public interface AuthService {

    AuthResponse register(
            RegisterRequest request
    );


    AuthResponse login(
            LoginRequest request
    );


    void changePassword(
            String email,
            ChangePasswordRequest request
    );

}