package com.system.ws.listener;

import com.system.ws.domain.SystemUserPrincipal;
import com.system.ws.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener {
    private LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event){
        Object obj = event.getAuthentication().getPrincipal();
        if(obj instanceof SystemUserPrincipal){
            SystemUserPrincipal userPrincipal = (SystemUserPrincipal) event.getAuthentication().getPrincipal();
            loginAttemptService.deleteSystemUserFromLoginAttemptCache(userPrincipal.getUsername());
        }
    }
}
