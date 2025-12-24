package com.example.bankapp.controller;

import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.entity.TransactionHistory;
import com.example.bankapp.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountService accountService;

    // Get own account details
    @GetMapping("/account/{accountNumber}")
    @PreAuthorize("hasRole('USER')")
    public AccountDto getMyAccount(@PathVariable Long accountNumber) {
        return accountService.getAccountDetailsByAccountNumber(accountNumber);
    }

    // Deposit money
    @PostMapping("/account/{accountNumber}/deposit")
    @PreAuthorize("hasRole('USER')")
    public AccountDto deposit(@PathVariable Long accountNumber,
                              @RequestParam Double amount) {
        return accountService.depositAmount(accountNumber, amount);
    }

    // Withdraw money
    @PostMapping("/account/{accountNumber}/withdraw")
    @PreAuthorize("hasRole('USER')")
    public AccountDto withdraw(@PathVariable Long accountNumber,
                               @RequestParam Double amount) {
        return accountService.withdrawAmount(accountNumber, amount);
    }

    // Transfer money
    @PostMapping("/account/transfer")
    @PreAuthorize("hasRole('USER')")
    public String transfer(@RequestParam Long fromAccount,
                           @RequestParam Long toAccount,
                           @RequestParam Double amount) {
        accountService.transferAmount(fromAccount, toAccount, amount);
        return "Transfer successful";
    }
    @GetMapping("/{accountNumber}/mini-statement")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TransactionHistory>> miniStatement(
            @PathVariable Long accountNumber,
            @RequestParam(defaultValue = "5") int count) {

        return ResponseEntity.ok(
                accountService.getMiniStatement(accountNumber, count)
        );
    }

    // Get transaction history
    @GetMapping("/account/{accountNumber}/transactions")
    @PreAuthorize("hasRole('USER')")
    public List<TransactionHistory> getTransactions(@PathVariable Long accountNumber) {
        return accountService.getTransactionHistory(accountNumber);
    }
}