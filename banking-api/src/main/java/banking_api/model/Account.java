package banking_api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(
            unique = true,
            nullable = false
    )
    private String accountNumber;



    @Column(
            precision = 19,
            scale = 2,
            nullable = false
    )
    private BigDecimal balance = BigDecimal.ZERO;



    @Column(nullable = false)
    private String accountHolder;



    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    @JsonIgnore
    private User user;



    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> transactions =
            new ArrayList<>();

}