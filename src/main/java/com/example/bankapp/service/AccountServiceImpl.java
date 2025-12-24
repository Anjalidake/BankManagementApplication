package com.example.bankapp.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankapp.mapper.AccountMapper;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.AccountStatus;
import com.example.bankapp.entity.TransactionHistory;
import com.example.bankapp.exception.AccountException;
import com.example.bankapp.repository.AccountRepository;
import com.example.bankapp.repository.TransactionHistoryRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repo;
    
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    // ---------------- CREATE ACCOUNT ----------------
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        account.setStatus(AccountStatus.ACTIVE); // default status
        Account savedAccount = repo.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // ---------------- GET ACCOUNT BY ACCOUNT NUMBER ----------------
    @Override
    public AccountDto getAccountDetailsByAccountNumber(Long accountNumber) {
        Account account = repo.findById(accountNumber)
                .orElseThrow(() -> new AccountException("Account not exists"));
        return AccountMapper.mapToAccountDto(account);
    }

    // ---------------- GET ALL ACCOUNTS ----------------
    @Override
    public List<AccountDto> getAllAccountdetails() {
        return repo.findAll()
                .stream()
                .map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    // ---------------- DEPOSIT AMOUNT ----------------
    @Override
    public AccountDto depositAmount(Long accountNumber, Double amount) {
        Account account = repo.findById(accountNumber)
                .orElseThrow(() -> new AccountException("Account not exists"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountException(
                    "Deposit not allowed. Account is " + account.getStatus()
            );
        }

        account.setAccountBalance(account.getAccountBalance() + amount);
        Account savedAccount = repo.save(account);

        // Log transaction history
        TransactionHistory transaction = new TransactionHistory(
                accountNumber,
                "DEPOSIT",
                amount,
                savedAccount.getAccountBalance(),
                "Deposit successful"
        );
        transactionHistoryRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // ---------------- WITHDRAW AMOUNT ----------------
    @Override
    public AccountDto withdrawAmount(Long accountNumber, Double amount) {
        Account account = repo.findById(accountNumber)
                .orElseThrow(() -> new AccountException("Account not exists"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountException(
                    "Withdraw not allowed. Account is " + account.getStatus()
            );
        }

        if (account.getAccountBalance() < amount) {
            throw new AccountException("Insufficient balance");
        }

        account.setAccountBalance(account.getAccountBalance() - amount);
        Account savedAccount = repo.save(account);

        // Log transaction history
        TransactionHistory transaction = new TransactionHistory(
                accountNumber,
                "WITHDRAWAL",
                amount,
                savedAccount.getAccountBalance(),
                "Withdrawal successful"
        );
        transactionHistoryRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // ---------------- TRANSFER AMOUNT ----------------
    @Override
    public void transferAmount(Long fromAccountNumber, Long toAccountNumber, Double amount) {
        Account fromAccount = repo.findById(fromAccountNumber)
                .orElseThrow(() -> new AccountException("Sender account not exists"));
        Account toAccount = repo.findById(toAccountNumber)
                .orElseThrow(() -> new AccountException("Receiver account not exists"));

        if (fromAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountException("Transfer not allowed. Sender account is " + fromAccount.getStatus());
        }
        if (toAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountException("Transfer not allowed. Receiver account is " + toAccount.getStatus());
        }

        if (fromAccount.getAccountBalance() < amount) {
            throw new AccountException("Insufficient balance in sender account");
        }

        // Deduct from sender
        fromAccount.setAccountBalance(fromAccount.getAccountBalance() - amount);
        Account updatedFrom = repo.save(fromAccount);

        // Add to receiver
        toAccount.setAccountBalance(toAccount.getAccountBalance() + amount);
        Account updatedTo = repo.save(toAccount);

        // Log transaction for sender
        TransactionHistory senderTransaction = new TransactionHistory(
                fromAccountNumber,
                "TRANSFER",
                amount,
                updatedFrom.getAccountBalance(),
                "Transferred to account " + toAccountNumber
        );
        transactionHistoryRepository.save(senderTransaction);

        // Log transaction for receiver
        TransactionHistory receiverTransaction = new TransactionHistory(
                toAccountNumber,
                "TRANSFER",
                amount,
                updatedTo.getAccountBalance(),
                "Received from account " + fromAccountNumber
        );
        transactionHistoryRepository.save(receiverTransaction);
    }

    // ---------------- CLOSE ACCOUNT (SOFT DELETE) ----------------
    @Override
    public void closeAccount(Long accountNumber) {
        Account account = repo.findById(accountNumber)
                .orElseThrow(() -> new AccountException("Account not exists"));

        account.setStatus(AccountStatus.CLOSED);
        repo.save(account);
    }

	@Override
	public List<TransactionHistory> getTransactionHistory(Long accountNumber) {
		

	    return transactionHistoryRepository
	            .findByAccountNumber(accountNumber);
	}

	@Override
	public List<TransactionHistory> getMiniStatement(Long accountNumber, int count) {
		return transactionHistoryRepository
	            .findByAccountNumber(accountNumber)
	            .stream()
	            .sorted(
	                Comparator.comparing(TransactionHistory::getTimestamp)
	                          .reversed()
	            )
	            .limit(count)
	            .toList();
	} 
	
	  
    
}
