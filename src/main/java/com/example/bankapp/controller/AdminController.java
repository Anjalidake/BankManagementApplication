
package com.example.bankapp.controller;

import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    // Get all accounts
    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccountdetails(); // Matches interface
    }

    // Create account
    @PostMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountDto createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    // Close account
    @DeleteMapping("/account/{accountNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAccount(@PathVariable Long accountNumber) {
        accountService.closeAccount(accountNumber);
        return "Account deleted successfully";
    }
}