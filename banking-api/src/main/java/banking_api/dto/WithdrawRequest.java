package banking_api.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class WithdrawRequest {


    @NotNull(
            message = "Amount is required"
    )
    @Positive(
            message = "Amount must be greater than zero"
    )
    private BigDecimal amount;

}