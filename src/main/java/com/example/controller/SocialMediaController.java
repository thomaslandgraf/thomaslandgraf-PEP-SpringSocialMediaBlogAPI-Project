package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

  private AccountService accountService;
  private MessageService messageService;

  public SocialMediaController(AccountService accountService, MessageService messageService){
    this.accountService = accountService;
    this.messageService = messageService;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<Account> insertAccount(@RequestBody Account account){
    try {
      Account accountAdded = accountService.insertAccount(account);

      return ResponseEntity.status(HttpStatus.OK).body(accountAdded);
    
    } catch (IllegalArgumentException e){

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    
    } catch (IllegalStateException e){

      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PostMapping(value = "/login")
  public ResponseEntity<Account> accountLogin(@RequestBody Account account){
    try{
      Account authenticatedAccount = accountService.accountLogin(account);

      return ResponseEntity.status(HttpStatus.OK).body(authenticatedAccount);

    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
  }
}
