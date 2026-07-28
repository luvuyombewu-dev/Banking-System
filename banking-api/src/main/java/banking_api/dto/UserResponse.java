package banking_api.dto;


import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {


    private Long id;


    private String firstName;


    private String lastName;


    private String email;


    private String role;


    private BigDecimal balance;

}