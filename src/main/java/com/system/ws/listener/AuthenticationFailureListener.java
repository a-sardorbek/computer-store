package com.system.ws.listener;

import com.system.ws.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener {
     private LoginAttemptService loginAttemptService;

     @Autowired
     public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event){
         Object obj = event.getAuthentication().getPrincipal();
         if(obj instanceof String){
             String username = (String) event.getAuthentication().getPrincipal();
             loginAttemptService.addSystemUserToLoginAttemptCache(username);
         }
    }
}
