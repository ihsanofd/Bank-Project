package com.Bank.Controller;

import com.Bank.Dto.TransactionRequest;
import com.Bank.Dto.TransactionResponse;
import com.Bank.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> doTransaction(@RequestBody TransactionRequest request){
        TransactionResponse response=transactionService.addTransaction(request);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }
}
