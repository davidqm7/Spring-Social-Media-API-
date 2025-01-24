package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    //function to save message
    public Message saveMessage(Message message)
    {
        //makes sure message is not blank or null
        if(message.getMessageText() == null || message.getMessageText().isBlank())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be blank!");
        }
        //makes sure message is not over 255 characters
        if(message.getMessageText().length() > 255)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be longer than 255 characters");
    
        }
        return messageRepository.save(message);
    }

    //finding a message by its id
    public Optional<Message> findMessageId(Integer messageId)
    {
        return messageRepository.findById(messageId);
    }

    //returns list of all messages
    public List<Message> getAllMessages()
    {
        return messageRepository.findAll(); 
    }

    //deletes a message by its id
    public int deleteMessageById(Integer messageId)
    {   //checks if message is found or not
        if(!messageRepository.existsById(messageId))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message with ID: " +messageId + " not found!");
        }
        messageRepository.deleteById(messageId);
        return 1;
    }

    //gets message by a specific user
    public List<Message> findMessageByPostedBy (Integer postedBy)
    {
        
        return messageRepository.findByPostedBy(postedBy);
       
    }

    //updates a message
    public int updateMessage (Integer messageId, String messageText)
    {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isEmpty())
        {
            return 0;
        }
        Message message = optionalMessage.get();
        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1; 
    }
}
