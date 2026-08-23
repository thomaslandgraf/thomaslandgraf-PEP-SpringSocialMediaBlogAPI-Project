package com.example.controller;

import java.util.List;
import java.util.Optional;

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

  private final AccountService accountService;
  private final MessageService messageService;

  public SocialMediaController(AccountService accountService, MessageService messageService){
    this.accountService = accountService;
    this.messageService = messageService;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<Account> insertAccount(@RequestBody Account account){
    Account accountAdded = accountService.insertAccount(account);
    return ResponseEntity.ok().body(accountAdded);
  }

  @PostMapping(value = "/login")
  public ResponseEntity<Account> accountLogin(@RequestBody Account account){
    Account authenticatedAccount = accountService.accountLogin(account);
    return ResponseEntity.ok().body(authenticatedAccount);
  }

  @PostMapping(value = "/messages")
  public ResponseEntity<Message> insertMessage(@RequestBody Message message){
      Message messageAdded = messageService.insertMessage(message);
      return ResponseEntity.ok().body(messageAdded);
  }

  @GetMapping(value = "/messages")
  public ResponseEntity<List<Message>> getAllMessages(){
    List<Message> messagesList = messageService.getAllMessages();
    return ResponseEntity.ok().body(messagesList);
  }

  @GetMapping(value = "/messages/{messageId}")
  public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
    return messageService.getMessageById(messageId)
      .map(m -> ResponseEntity.ok().body(m))
      .orElseGet(() -> ResponseEntity.ok().build());
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
    Integer messageUpdate = messageService.updateMessageById(messageId, message);
    return ResponseEntity.ok().body(messageUpdate);
  }

  @GetMapping(value = "/accounts/{accountId}/messages")
  public ResponseEntity<List<Message>> messagesByPostedBy(@PathVariable int accountId){
    List<Message> messagesList = messageService.messagesByPostedBy(accountId);
    return ResponseEntity.ok().body(messagesList);

  }
}
