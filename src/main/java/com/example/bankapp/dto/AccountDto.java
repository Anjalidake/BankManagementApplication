package com.example.bankapp.dto;

import java.time.LocalDateTime;

import com.example.bankapp.entity.AccountStatus;

public class AccountDto {

    private Long accountNumber;
    private String accountHolderName;
    private Double accountBalance;
    private AccountStatus status;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ✅ REQUIRED
    public AccountDto() {
    }

	public AccountDto(Long accountNumber, String accountHolderName, Double accountBalance, AccountStatus status,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.accountBalance = accountBalance;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

   
    
}
