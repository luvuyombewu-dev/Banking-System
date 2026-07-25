package banking_api.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {


    private String receiverAccountNumber;


    private Double amount;

}