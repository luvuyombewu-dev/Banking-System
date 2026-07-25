package banking_api.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {


    private Long id;

    private String type;

    private Double amount;

    private LocalDateTime date;

}