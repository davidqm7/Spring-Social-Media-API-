package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.h2.security.auth.AuthConfigException;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;


@Service
public class AccountService {
    
    private List<Account> accountList = new ArrayList<>();
    private Message message; 

    public void register (Account account) 
    {
        accountList.add(account); 
    }
    
    public void login(String username, String password) throws AuthenticationException
    {
        for(Account account: accountList)
        {
            if(account.getUsername().equals(username) && account.getPassword().equals(password))
            {
                return;
            }
            throw new AuthenticationException("Username or Password are invalid!");
        }

    }

    

}
