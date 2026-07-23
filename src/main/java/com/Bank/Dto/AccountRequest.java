package com.Bank.Dto;

import com.Bank.Enum.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    private String username;
    private String fullName;
    private AccountType accountType;
    private BigDecimal balance;
}
