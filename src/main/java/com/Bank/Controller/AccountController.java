package com.Bank.Controller;

import com.Bank.Dto.AccountRequest;
import com.Bank.Dto.AccountResponse;
import com.Bank.Service.AccountService;
import com.Bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) {
        AccountResponse accountResponse=accountService.createAccount(request);
        return new ResponseEntity<>(accountResponse , HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable Long id){
        AccountResponse response=accountService.findById(id);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }
}
