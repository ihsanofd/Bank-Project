package com.Bank.Service;

import com.Bank.Dto.TransactionRequest;
import com.Bank.Dto.TransactionResponse;
import com.Bank.Enum.Status;
import com.Bank.Repository.AccountRepository;
import com.Bank.Repository.TransactionRepository;
import com.Bank.model.Account;
import com.Bank.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public TransactionResponse addTransaction(TransactionRequest request) {

        Account account=accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(()->new IllegalArgumentException("account not found"));


        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        Transaction transaction=new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setAccount(account);
        transaction.setStatus(Status.DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());

        Transaction saved=transactionRepository.save(transaction);

        TransactionResponse response=new TransactionResponse();
        response.setId(saved.getId());
        response.setAmount(saved.getAmount());
        response.setAccountNumber(saved.getAccount().getAccountNumber());
        response.setStatus(saved.getStatus());
        response.setTimestamp(saved.getTimestamp());

        return response;

    }
}
