package banking_api.service.impl;


import banking_api.dto.UserResponse;
import banking_api.exception.ResourceNotFoundException;
import banking_api.model.User;
import banking_api.repository.UserRepository;
import banking_api.service.UserService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;



    public UserServiceImpl(
            UserRepository userRepository
    ) {

        this.userRepository = userRepository;

    }



    @Override
    public UserResponse getUserById(Long id) {


        User user =
                userRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );



        return mapToResponse(user);

    }




    private UserResponse mapToResponse(
            User user
    ) {


        BigDecimal balance = null;



        if(user.getAccount() != null) {

            balance =
                    user.getAccount()
                            .getBalance();

        }



        return new UserResponse(

                user.getId(),

                user.getFirstName(),

                user.getLastName(),

                user.getEmail(),

                user.getRole(),

                balance

        );

    }

}