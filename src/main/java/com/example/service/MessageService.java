package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidFieldException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

  private final MessageRepository messageRepository;
  private final AccountRepository accountRepository;

  public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
    this.messageRepository = messageRepository;
    this.accountRepository = accountRepository;
  }

  public Message insertMessage(Message message){
    if(hasInvalidMessageText(message)){
      throw new InvalidFieldException("Please check your input. Some required fields are blank or exceed the character limit.");
    }
    boolean userExist = accountRepository.existsById(message.getPostedBy());
    if(!userExist){
      throw new InvalidFieldException("User not found.");
    }
    return messageRepository.save(message);
  }

  public List<Message> getAllMessages(){
    return messageRepository.findAll();
  }

  public Optional<Message> getMessageById(int id){
    return messageRepository.findById(id);
  }

  public Integer deleteMessageById(int id){
    Optional<Message> message = messageRepository.findById(id);
    if(message.isPresent()){
      messageRepository.deleteById(id);
      return 1;
    }
    return 0;
  }

  public Integer updateMessageById(Integer id, Message message){
    if(hasInvalidMessageText(message)){
      throw new InvalidFieldException("Please check your input. Some required fields are blank or exceed the character limit.");
    }
    Optional<Message> optionalMessage = messageRepository.findById(id);
    if(!optionalMessage.isPresent()){
      throw new InvalidFieldException("Message not found.");
    }
    Message updateMessage = optionalMessage.get();
    updateMessage.setMessageText(message.getMessageText());
    messageRepository.save(updateMessage);
    return 1;
  }

  public List<Message> messagesByPostedBy(int id){
    return messageRepository.findByPostedBy(id);
  }

  private boolean hasInvalidMessageText(Message message){
    return message.getMessageText() == null 
    || message.getMessageText().length() > 255 
    || message.getMessageText().trim().isEmpty();
  }
}
