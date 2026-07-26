package banking_api.dto;


import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {


    @NotBlank(message="Receiver account number is required")
    private String receiverAccountNumber;



    @NotNull(message="Amount is required")
    @Positive(message="Amount must be positive")
    private Double amount;

}