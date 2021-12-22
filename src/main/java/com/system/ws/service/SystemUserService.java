package com.system.ws.service;

import com.system.ws.domain.entity.SystemUser;
import com.system.ws.exception.PasswordMatchException;
import com.system.ws.exception.UsernameExistException;

import java.io.IOException;

public interface SystemUserService {
    SystemUser register(String fio, String username, String password1, String password2) throws UsernameExistException, IOException, PasswordMatchException;

    SystemUser findUserByUsername(String username);
}
