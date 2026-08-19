package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

  private AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository){
    this.accountRepository = accountRepository;
  }

  public Account insertAccount(Account account){
    if(account.getUsername() == null || account.getUsername().trim().isEmpty()){
      throw new IllegalArgumentException();
    }

    if(account.getPassword().length() < 4){
      throw new IllegalArgumentException();
    }

    Optional<Account> accountExists = accountRepository.findByUsername(account.getUsername());
    if(accountExists.isPresent()){
      throw new IllegalStateException();
    }
    
    return accountRepository.save(account);
  }
}
