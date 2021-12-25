package com.system.ws.service.impl;

import com.system.ws.constant.SystemUserConstant;
import com.system.ws.domain.SystemUserPrincipal;
import com.system.ws.domain.entity.SystemUser;
import com.system.ws.enumeration.Role;
import com.system.ws.exception.PasswordMatchException;
import com.system.ws.exception.UsernameExistException;
import com.system.ws.repository.ProductRepo;
import com.system.ws.repository.SystemUserRepo;
import com.system.ws.service.LoginAttemptService;
import com.system.ws.service.SystemUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.util.Date;

@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserService, UserDetailsService {

    private static final String IMAGE_BASE_URL = "https://robohash.org/";
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private SystemUserRepo systemUserRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private LoginAttemptService loginAttemptService;
    private ProductRepo productRepo;

    @Autowired
    public SystemUserServiceImpl(SystemUserRepo systemUserRepo
                                ,BCryptPasswordEncoder bCryptPasswordEncoder
                                ,LoginAttemptService loginAttemptService
                                ,ProductRepo productRepo) {
        this.systemUserRepo = systemUserRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.productRepo = productRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      SystemUser systemUser = systemUserRepo.findUserByUsername(username);
      if(systemUser == null){
          LOGGER.error(SystemUserConstant.NO_USER_FOUND_BY_USERNAME+username);
          throw new UsernameNotFoundException(SystemUserConstant.NO_USER_FOUND_BY_USERNAME+username);
      }else{
         validateLoginAttempt(systemUser); // this is for login attempt count

          systemUser.setLastLoginDate(new Date());
          systemUser.setLastLoginDateDisplay(systemUser.getLastLoginDate());
          systemUserRepo.save(systemUser);
          SystemUserPrincipal principal = new SystemUserPrincipal(systemUser);
          return principal;
      }
    }


    @Override
    public SystemUser register(String fio, String username, String password1, String password2) throws UsernameExistException, IOException, PasswordMatchException {
        validateNewSystemUser(username);
        SystemUser systemUser = new SystemUser();
        String password = repeatPassword(password1,password2);
        systemUser.setFio(fio);
        systemUser.setUsername(username);
        systemUser.setUserId(generateSystemUserId());
        systemUser.setPassword(password);
        systemUser.setJoinDate(new Date());
        systemUser.setActive(true);
        systemUser.setNotLocked(true);
        systemUser.setProfileImage(getImageFromURL(username));
        systemUser.setRole(Role.ROLE_SYSTEM_USER.name());
        systemUser.setAuthorities(Role.ROLE_SYSTEM_USER.getAuthorities());
        systemUserRepo.save(systemUser);

        return systemUser;
    }

    @Override
    public SystemUser findUserByUsername(String username) {
        return systemUserRepo.findUserByUsername(username);
    }


    private void validateLoginAttempt(SystemUser systemUser) {
        if(systemUser.isNotLocked()){
            if(loginAttemptService.hasExceededMaxAttempts(systemUser.getUsername())){
                systemUser.setNotLocked(false);
            }else{
                systemUser.setNotLocked(true);
            }
        }else {
            loginAttemptService.deleteSystemUserFromLoginAttemptCache(systemUser.getUsername());
        }
    }

    private byte[] getImageFromURL(String username) throws IOException{
        URL url = new URL(IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()){
            int bytesRead; // this is equal to zero because it is primitive integer
            byte[] chunk = new byte[1048];
            while((bytesRead = inputStream.read(chunk)) > 0){
                byteArrayOutputStream.write(chunk,0,bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();

    }

    private String generateSystemUserId() {
        return RandomStringUtils.randomNumeric(7);
    }

    private String repeatPassword(String password1, String password2) throws PasswordMatchException {
        if(password1.equals(password2)){
            return bCryptPasswordEncoder.encode(password1);
        }else{
            throw new PasswordMatchException(SystemUserConstant.PASSWORD_MATCH);
        }
    }

    private void validateNewSystemUser(String username) throws UsernameExistException {
        SystemUser newSystemUser = systemUserRepo.findUserByUsername(username);
        if(newSystemUser != null){
            throw new UsernameExistException(SystemUserConstant.USERNAME_ALREADY_EXISTS);
        }
    }


}
