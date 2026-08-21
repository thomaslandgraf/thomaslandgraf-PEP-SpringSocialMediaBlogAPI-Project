package com.example.controller;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidFieldException;
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

      return ResponseEntity.ok().body(accountAdded);
    
    } catch (InvalidFieldException e){

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    
    } catch (IllegalStateException e){

      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PostMapping(value = "/login")
  public ResponseEntity<Account> accountLogin(@RequestBody Account account){
    try{
      Account authenticatedAccount = accountService.accountLogin(account);

      return ResponseEntity.ok().body(authenticatedAccount);

    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
  }

  @PostMapping(value = "/messages")
  public ResponseEntity<Message> insertMessage(@RequestBody Message message){
    try {

      Message messageAdded = messageService.insertMessage(message);

      return ResponseEntity.ok().body(messageAdded);

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping(value = "/messages")
  public ResponseEntity<List<Message>> getAllMessages(){
    List<Message> messagesList = messageService.getAllMessages();
    return ResponseEntity.ok().body(messagesList);
  }

  @GetMapping(value = "/messages/{messageId}")
  public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
    Optional<Message> message = messageService.getMessageById(messageId);

    if(message.isPresent()){
      return ResponseEntity.ok().body(message.get());
    } else {
      return ResponseEntity.ok().build();
    }
  }

  @DeleteMapping(value = "/messages/{messageId}")
  public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
    Integer numberOfDeletedRecord = messageService.deleteMessageById(messageId);
    if(numberOfDeletedRecord == 1){
      return ResponseEntity.ok().body(numberOfDeletedRecord);
    } else {
      return ResponseEntity.ok().build();
    }
  }

  @PatchMapping(value = "/messages/{messageId}")
  public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message message){
    Integer numberOfUpdatedRecord = messageService.updateMessageById(messageId, message);
    if(numberOfUpdatedRecord == 1){
      return ResponseEntity.ok().body(numberOfUpdatedRecord);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping(value = "/accounts/{accountId}/messages")
  public ResponseEntity<List<Message>> messagesByPostedBy(@PathVariable int accountId){
    List<Message> messagesList = messageService.messagesByPostedBy(accountId);
    return ResponseEntity.ok().body(messagesList);

  }
}
