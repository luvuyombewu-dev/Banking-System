package banking_api.service;


import banking_api.dto.UserResponse;


public interface UserService {


    UserResponse getUserById(Long id);

}