package com.Bank.Dto;

import com.Bank.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransferResponse {
    private Long creditTransactionId;
    private Long debitTransactionId;
    private String FromAccountNumber;
    private String ToAccountNumber;
    private BigDecimal amount;
    private Status status;
    private LocalDateTime timestamp;
}