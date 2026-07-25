package banking_api.model;

import jakarta.persistence.*;
import lombok.*;

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


    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    @Column(nullable = false)
    private String type;


    @Column(nullable = false)
    private Double amount;


    @Column(nullable = false)
    private LocalDateTime date;
}