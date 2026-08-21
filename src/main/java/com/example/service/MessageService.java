package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

  private MessageRepository messageRepository;
  private AccountRepository accountRepository;

  public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
    this.messageRepository = messageRepository;
    this.accountRepository = accountRepository;
  }

  public Message insertMessage(Message message){
    if(message.getMessageText() == null || message.getMessageText().length() > 255 || message.getMessageText().trim().isEmpty()){
      throw new IllegalArgumentException();
    }

    boolean userExist = accountRepository.existsById(message.getPostedBy());

    if(userExist){
      return messageRepository.save(message);
    }

    throw new IllegalArgumentException();
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
    if(message.getMessageText() == null || message.getMessageText().trim().isEmpty() || message.getMessageText().length() > 255){
      return 0;
    }
   
    Optional<Message> optionalMessage = messageRepository.findById(id);

    if(optionalMessage.isPresent()){
      Message updateMessage = optionalMessage.get();
      updateMessage.setMessageText(message.getMessageText());
      messageRepository.save(updateMessage);
      return 1;
    }
    return 0;
  }

  public List<Message> messagesByPostedBy(int id){
    List<Message> messagesList = messageRepository.findByPostedBy(id);
    return messagesList;
  }
}
