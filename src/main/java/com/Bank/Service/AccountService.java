package com.Bank.Service;

import com.Bank.Dto.AccountRequest;
import com.Bank.Dto.AccountResponse;
import com.Bank.Repository.AccountRepository;
import com.Bank.Repository.CustomerRepository;
import com.Bank.model.Account;
import com.Bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class AccountService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;


    public AccountResponse createAccount(AccountRequest request) {

        Customer customer=customerRepository.findByUser_Username(request.getUsername()).orElseThrow(
                ()-> new UsernameNotFoundException("Customer not found for this username"));

        Account account=new Account();
        account.setAccountType(request.getAccountType());
        account.setAccountNumber(String.valueOf(System.currentTimeMillis()));
        account.setBalance(request.getBalance() != null ? request.getBalance() : BigDecimal.ZERO);
        account.setCreatedAt(LocalDate.now());
        account.setCustomer(customer);

        Account saved=accountRepository.save(account);

        AccountResponse accountResponse=new AccountResponse();
        accountResponse.setAccountNumber(saved.getAccountNumber());
        accountResponse.setId(saved.getId());
        accountResponse.setAccountType(saved.getAccountType());
        accountResponse.setBalance(saved.getBalance());

        return accountResponse;
    }

    public AccountResponse findById(Long id) {
        Account account=accountRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("account not found"));

        AccountResponse response=new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        return response;
    }
}
