package banking_api.service.impl;

import banking_api.dto.TransactionResponse;
import banking_api.exception.BadRequestException;
import banking_api.exception.ResourceNotFoundException;
import banking_api.model.Account;
import banking_api.model.Transaction;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.repository.TransactionRepository;
import banking_api.service.TransferService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferServiceImpl(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public TransactionResponse transfer(
            User sender,
            String receiverAccountNumber,
            Double amount
    ) {

        if (amount == null || amount <= 0) {
            throw new BadRequestException("Amount must be greater than zero");
        }

        Account senderAccount =
                accountRepository.findByUser(sender)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Sender account not found"
                                )
                        );

        Account receiverAccount =
                accountRepository.findByAccountNumber(receiverAccountNumber)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Receiver account not found"
                                )
                        );

        if (senderAccount.getId().equals(receiverAccount.getId())) {
            throw new BadRequestException("Cannot transfer to same account");
        }

        if (senderAccount.getBalance() == null) {
            senderAccount.setBalance(0.0);
        }

        if (receiverAccount.getBalance() == null) {
            receiverAccount.setBalance(0.0);
        }

        if (senderAccount.getBalance() < amount) {
            throw new BadRequestException("Insufficient balance");
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        Transaction senderTransaction = new Transaction();
        senderTransaction.setAccount(senderAccount);
        senderTransaction.setType("TRANSFER_OUT");
        senderTransaction.setAmount(amount);
        senderTransaction.setDate(LocalDateTime.now());

        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setAccount(receiverAccount);
        receiverTransaction.setType("TRANSFER_IN");
        receiverTransaction.setAmount(amount);
        receiverTransaction.setDate(LocalDateTime.now());

        senderAccount.getTransactions().add(senderTransaction);
        receiverAccount.getTransactions().add(receiverTransaction);

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        Transaction saved = transactionRepository.save(senderTransaction);
        transactionRepository.save(receiverTransaction);

        return new TransactionResponse(
                saved.getId(),
                saved.getType(),
                saved.getAmount(),
                saved.getDate()
        );
    }
}