package banking_api.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name="accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(unique = true, nullable = false)
    private String accountNumber;



    private String accountHolder;



    private Double balance = 0.0;



    @OneToOne
    @JoinColumn(name="user_id")
    private User user;



    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL
    )
    private List<Transaction> transactions =
            new ArrayList<>();

}