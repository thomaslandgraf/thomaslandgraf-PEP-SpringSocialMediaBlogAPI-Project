package com.example.service;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.InvalidFieldException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

  private AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository){
    this.accountRepository = accountRepository;
  }

  public Account insertAccount(Account account){
    if(account.getUsername() == null || account.getUsername().trim().isEmpty()){
      throw new InvalidFieldException("Please check your input. Some required fields are blank or null.");
    }

    if(account.getPassword() == null || account.getPassword().length() < 4){
      throw new InvalidFieldException("Please check your input. Some required fields are blank, null.");
    }

    Optional<Account> accountExists = accountRepository.findByUsername(account.getUsername());
    if(accountExists.isPresent()){
      throw new IllegalStateException("Account already exist.");
    }
    
    return accountRepository.save(account);
  }

  public Account accountLogin(Account account) throws AuthenticationException{
    Optional<Account> accountFound = accountRepository.findByUsername(account.getUsername());

    if(accountFound.isPresent()){
      Account dataBaseAccount = accountFound.get();

      if(dataBaseAccount.getPassword().equals(account.getPassword())){
        return dataBaseAccount;
      }
    }
    throw new AuthenticationException("Invalid username or password");
  }
}
