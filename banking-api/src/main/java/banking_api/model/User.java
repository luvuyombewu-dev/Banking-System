package banking_api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false)
    private String firstName;



    @Column(nullable = false)
    private String lastName;



    @Column(
            unique = true,
            nullable = false
    )
    private String email;



    @JsonIgnore
    @Column(nullable = false)
    private String password;



    @Column(nullable = false)
    private String role = "ROLE_USER";



    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Account account;

}