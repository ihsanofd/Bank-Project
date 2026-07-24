package com.Bank.Controller;

import com.Bank.Dto.TransactionRequest;
import com.Bank.Dto.TransactionResponse;
import com.Bank.Dto.TransferRequest;
import com.Bank.Dto.TransferResponse;
import com.Bank.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody TransactionRequest request){
        TransactionResponse response=transactionService.withdraw(request);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request){
        TransferResponse response=transactionService.transfer(request);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }
}
