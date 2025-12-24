package com.example.bankapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankapp.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	

	

}