package banking_api.service;

import banking_api.dto.AuthResponse;
import banking_api.dto.LoginRequest;
import banking_api.dto.RegisterRequest;
import banking_api.dto.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}