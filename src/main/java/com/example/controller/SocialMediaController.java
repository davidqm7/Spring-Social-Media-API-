package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    //MessageService for business logic
    private final MessageService messageService; 
    private final AccountService accountService; 

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService)
    {
        this.messageService = messageService;
        this.accountService = accountService;
    }

    //POST endpoint to register a new account
    @PostMapping ("/register")
    public Account registerAccount(@RequestBody Account account)
    {
        return accountService.register(account); 
    }

    //POST endpoint to login a user
    @PostMapping("/login")
    public Account login(@RequestBody Account account)
    {
        try {
            return accountService.login(account.getUsername(), account.getPassword());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password", e);
        }
    }

    //POST endpoint to create and save a message
    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public Message postMessage(@RequestBody Message message)
    {
        return messageService.saveMessage(message); 
    }

    //GET endpoint to get all messages
    @GetMapping("/messages")
    public List<Message> getAllMessages()
    {
        return messageService.getAllMessages(); 
    }

    //GET endpoint to get a message by a specific ID, throw exception if not found
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageByID(@PathVariable  Integer messageId)
    {
        Optional<Message> message = messageService.findMessageId(messageId);
    if (message.isPresent()) {
        return ResponseEntity.ok(message.get()); 
    } else {
        return ResponseEntity.ok().build(); 
    }
    } 
    
    //DELETE endpoint to delete a message using it ID
    @DeleteMapping("/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId)
    {
        int rows = messageService.deleteMessageById(messageId);
        return ResponseEntity.ok(rows); 
       
    }

    //GET endpoint to get message from a specific user
    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessageByUserID (@PathVariable Integer accountId)
    {
        return messageService.findMessageByPostedBy(accountId);
    }

    //Patch endpoint to update a message
    @PatchMapping("/messages/{messageId}")
    public int updateMessage (@PathVariable Integer messageId, @RequestBody Map<String, String> body)
    {
        String messageText = body.get("messageText");
        if(messageText == null || messageText.length() > 255 || messageText.isBlank())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid message");
        }

        int rows = messageService.updateMessage(messageId, messageText);
        if(rows == 0)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updating message failed.");
        }
        return rows; 
    }
    
}
