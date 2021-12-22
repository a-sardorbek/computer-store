package com.system.ws.resource;

import com.system.ws.constant.SecurityConstant;
import com.system.ws.domain.SystemUserPrincipal;
import com.system.ws.domain.entity.SystemUser;
import com.system.ws.exception.PasswordMatchException;
import com.system.ws.exception.SystemUserNotFoundException;
import com.system.ws.exception.UsernameExistException;
import com.system.ws.service.SystemUserService;
import com.system.ws.utility.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class SystemUserResource {


    private SystemUserService systemUserService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SystemUserResource(SystemUserService systemUserService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.systemUserService = systemUserService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<SystemUser> register(@RequestBody SystemUser user) throws SystemUserNotFoundException, UsernameExistException, PasswordMatchException, IOException {
        SystemUser newUser = systemUserService.register(user.getFio(),user.getUsername(),user.getPassword(), user.getPassword2());
        return new ResponseEntity<>(newUser, OK);

        // here we should validate email, name surname starts with upper case because for users we cannot trust
    }

    @PostMapping("/login")
    public ResponseEntity<SystemUser> login(@RequestBody SystemUser systemUser){
        authenticateUser(systemUser.getUsername(),systemUser.getPassword());
        SystemUser loginUser =systemUserService.findUserByUsername(systemUser.getUsername());
        SystemUserPrincipal systemUserPrincipal = new SystemUserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(systemUserPrincipal);
        return new ResponseEntity<>(loginUser,jwtHeader, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(SystemUserPrincipal systemUserPrincipal) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstant.JWT_TOKEN_HEADER,jwtTokenProvider.generateJwtToken(systemUserPrincipal));
        return httpHeaders;
    }

    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

}
