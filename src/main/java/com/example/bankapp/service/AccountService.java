package com.example.bankapp.service;

import java.util.List;

import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.TransactionHistory;

public interface AccountService {
	

	    AccountDto createAccount(AccountDto accountDto);

	    AccountDto getAccountDetailsByAccountNumber(Long accountNumber);

	    List<AccountDto> getAllAccountdetails();

	    AccountDto depositAmount(Long accountNumber, Double amount);

	    AccountDto withdrawAmount(Long accountNumber, Double amount);

	    void closeAccount(Long accountNumber);

		void transferAmount(Long fromAccountNumber, Long toAccountNumber, Double amount);

		List<TransactionHistory> getTransactionHistory(Long accountNumber);

		List<TransactionHistory> getMiniStatement(Long accountNumber, int count);

		
		 
		 }


