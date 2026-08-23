package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidFieldException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository){
    this.accountRepository = accountRepository;
  }

  public Account insertAccount(Account account){
    if(hasInvalidAccountFields(account)){
      throw new InvalidFieldException("Please check your input. Some required fields are blank or null.");
    }
    Optional<Account> accountExists = accountRepository.findByUsername(account.getUsername());
    if(accountExists.isPresent()){
      throw new DuplicateUsernameException("Account already exist.");
    }
    return accountRepository.save(account);
  }

  public Account accountLogin(Account account){
    Optional<Account> accountFound = accountRepository.findByUsername(account.getUsername());
    if(accountFound.isEmpty()){
      throw new UnauthorizedException("Invalid username or password");
    }
    Account dataBaseAccount = accountFound.get();
    if(!dataBaseAccount.getPassword().equals(account.getPassword())){
      throw new UnauthorizedException("Invalid username or password");
    }
    return dataBaseAccount;
  }

  private boolean hasInvalidAccountFields(Account account){
    return account.getUsername() == null 
    || account.getUsername().trim().isEmpty()
    || account.getPassword() == null
    || account.getPassword().length() < 4;
  }
}
