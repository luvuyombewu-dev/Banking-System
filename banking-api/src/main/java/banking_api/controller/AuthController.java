package banking_api.controller;


import banking_api.dto.AuthResponse;
import banking_api.dto.LoginRequest;
import banking_api.dto.RegisterRequest;
import banking_api.dto.UserResponse;
import banking_api.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
public class AuthController {



    private final UserService userService;



    public AuthController(
            UserService userService
    ) {

        this.userService = userService;
    }





    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {


        UserResponse response =
                userService.register(request);


        return ResponseEntity.ok(response);
    }






    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {


        AuthResponse response =
                userService.login(request);



        return ResponseEntity.ok(response);
    }

}