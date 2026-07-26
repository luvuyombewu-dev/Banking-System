package banking_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;


    private String lastName;


    @Column(unique = true, nullable = false)
    private String email;


    @JsonIgnore
    private String password;


    private String role;


    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;

}