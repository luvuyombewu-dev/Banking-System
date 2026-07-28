package banking_api.service;


import banking_api.dto.TransactionResponse;
import banking_api.model.User;

import java.math.BigDecimal;


public interface TransferService {


    TransactionResponse transfer(
            User sender,
            String receiverAccountNumber,
            BigDecimal amount
    );

}