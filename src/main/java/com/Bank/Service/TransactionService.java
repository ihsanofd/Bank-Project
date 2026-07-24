package com.Bank.Service;

import com.Bank.Dto.TransactionRequest;
import com.Bank.Dto.TransactionResponse;
import com.Bank.Dto.TransferRequest;
import com.Bank.Dto.TransferResponse;
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


    @Transactional
    public TransactionResponse withdraw(TransactionRequest request) {

        Account account=accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(()->new IllegalArgumentException("account not found"));

        if (request.getAmount().compareTo(account.getBalance())>0) {

          throw new IllegalArgumentException("insufficient balance");
        }
        Transaction transaction = new Transaction();
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        transaction.setAmount(request.getAmount());
        transaction.setStatus(Status.WITHDRAW);
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        Transaction saved = transactionRepository.save(transaction);

        TransactionResponse response = new TransactionResponse();
        response.setId(saved.getId());
        response.setAmount(saved.getAmount());
        response.setAccountNumber(saved.getAccount().getAccountNumber());
        response.setStatus(saved.getStatus());
        response.setTimestamp(saved.getTimestamp());
        return response;
    }


    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        Account fromAccount=accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(()->new IllegalArgumentException("source account not exist"));

        Account toAccount=accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(()->new IllegalArgumentException("destination account not found"));

        if (fromAccount.getAccountNumber() .equals( toAccount.getAccountNumber())){

            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        if (request.getAmount().compareTo(fromAccount.getBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        Transaction debit=new Transaction();

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        accountRepository.save(fromAccount);

        debit.setAccount(fromAccount);
        debit.setAmount(request.getAmount());
        debit.setStatus(Status.TRANSFER);
        debit.setTimestamp(LocalDateTime.now());

        Transaction debitSave=transactionRepository.save(debit);

        Transaction credit=new Transaction();
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        accountRepository.save(toAccount);

        credit.setAmount(request.getAmount());
        credit.setStatus(Status.TRANSFER);
        credit.setAccount(toAccount);
        credit.setTimestamp(LocalDateTime.now());

        Transaction creditSave=transactionRepository.save(credit);


        TransferResponse response=new TransferResponse();

        response.setCreditTransactionId(creditSave.getId());
        response.setDebitTransactionId(debitSave.getId());
        response.setToAccountNumber(creditSave.getAccount().getAccountNumber());
        response.setFromAccountNumber(debitSave.getAccount().getAccountNumber());
        response.setAmount(creditSave.getAmount());
        response.setTimestamp(creditSave.getTimestamp());
        response.setStatus(creditSave.getStatus());

        return response;
    }
}
