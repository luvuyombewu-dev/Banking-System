package banking_api.dto;


import banking_api.model.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {


    private Long id;


    private TransactionType type;


    private BigDecimal amount;


    private LocalDateTime date;

}