package com.example.bankapp.mapper;

import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.entity.Account;

public class AccountMapper {

    // Convert AccountDto to Account entity
    public static Account mapToAccount(AccountDto dto) {
        if (dto == null) return null;
        return new Account(
            dto.getAccountNumber(),
            dto.getAccountHolderName(),
            dto.getAccountBalance(),
            dto.getStatus(),
            dto.getCreatedAt(),
            dto.getUpdatedAt()
        );
    }

    // Convert Account entity to AccountDto
    public static AccountDto mapToAccountDto(Account account) {
        if (account == null) return null;
        AccountDto dto = new AccountDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountHolderName(account.getAccountHolderName());
        dto.setAccountBalance(account.getAccountBalance());
        dto.setStatus(account.getStatus());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());
        return dto;
    }
}
