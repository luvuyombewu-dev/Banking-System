package banking_api.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;



    @Column(
            precision = 19,
            scale = 2,
            nullable = false
    )
    private BigDecimal amount;



    @Column(nullable = false)
    private LocalDateTime date;



    @JsonBackReference
    @ManyToOne
    @JoinColumn(
            name = "account_id",
            nullable = false
    )
    private Account account;

}