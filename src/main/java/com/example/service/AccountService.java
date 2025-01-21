package com.example.service;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.repository.AccountRepository;




@Service
public class AccountService {
    
    private AccountRepository accountRepository;  

    @Autowired
    public AccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository; 
    }

    public Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    //Registers an account
    public Account register (Account account) 
    {   //checks if username is blank or null
        if(account.getUsername() == null || account.getUsername().isBlank())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be blank!");
        }
        //checks if the password is null or less than four characters
        if(account.getPassword() == null || account.getPassword().length() < 4)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 4 characters in length"); 
        }
        //checks if the username already exists
        if(accountRepository.findByUsername(account.getUsername()).isPresent())
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
        }

        return accountRepository.save(account);
    }
    

    //function to log the user in
    public Account login(String username, String password) throws AuthenticationException
    {   //check if the username and password is blank or null
        if(username == null || username.isBlank() || password == null || password.isBlank())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and Password cannot be blank!");
        }

        //verify it matches an existing an existing account
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return account.get();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password!");
    }

    

}
