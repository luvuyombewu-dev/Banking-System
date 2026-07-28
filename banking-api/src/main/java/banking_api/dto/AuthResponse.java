package banking_api.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {


    private String token;


    private Long userId;


    private String firstName;


    private String lastName;


    private String email;


    private String role;

}