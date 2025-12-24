package com.example.bankapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.dto.AmountRequest;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.TransactionHistory;
import com.example.bankapp.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("account")
@Validated
public class AccountController {

    @Autowired
    private AccountService service;

    // CREATE ACCOUNT
    @PostMapping("/created")
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDto> createAccount(
            @Valid @RequestBody AccountDto accountDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createAccount(accountDto));
    }

    // GET ACCOUNT BY NUMBER
    @GetMapping("/{accountNumber}")
     @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<AccountDto> getAccount(
            @PathVariable Long accountNumber) {
        return ResponseEntity.ok(
                service.getAccountDetailsByAccountNumber(accountNumber)
        );
    }

    // GET ALL ACCOUNTS (ADMIN ONLY)
    @GetMapping
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccountdetails());
    }

    // DEPOSIT
    @PutMapping("/{accountNumber}/deposit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountDto> deposit(
            @PathVariable Long accountNumber,
            @Valid @RequestBody AmountRequest amountRequest) {

        return ResponseEntity.ok(
                service.depositAmount(accountNumber, amountRequest.getAmount())
        );
    }

    // WITHDRAW
    @PutMapping("/{accountNumber}/withdraw")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountDto> withdraw(
            @PathVariable Long accountNumber,
            @Valid @RequestBody AmountRequest request) {

        return ResponseEntity.ok(
                service.withdrawAmount(accountNumber, request.getAmount())
        );
    }
    //Transfer
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long fromAccountNumber,
                                           @RequestParam Long toAccountNumber,
                                           @RequestParam Double amount) {
        service.transferAmount(fromAccountNumber, toAccountNumber, amount);
        return ResponseEntity.ok("Transfer successful");
    }

    // CLOSE ACCOUNT 
    @DeleteMapping("/{accountNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> closeAccount(
            @PathVariable Long accountNumber) {

        service.closeAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }
    
    
    // GET TRANSACTION HISTORY
    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<TransactionHistory>> getTransactionHistory(@PathVariable Long accountNumber) {
        return ResponseEntity.ok(service.getTransactionHistory(accountNumber));
    }
}
