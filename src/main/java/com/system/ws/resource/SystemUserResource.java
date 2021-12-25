package com.system.ws.resource;

import com.system.ws.constant.SecurityConstant;
import com.system.ws.domain.SystemUserPrincipal;
import com.system.ws.domain.entity.SystemUser;
import com.system.ws.dto.LoginSystemUser;
import com.system.ws.dto.RegisterSystemUser;
import com.system.ws.exception.PasswordMatchException;
import com.system.ws.exception.UsernameExistException;
import com.system.ws.service.SystemUserService;
import com.system.ws.utility.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path={"/","/user"})
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
    @PreAuthorize("hasAnyAuthority('system:create')")
    public ResponseEntity<?> register(@RequestBody RegisterSystemUser systemUser) throws  UsernameExistException, PasswordMatchException, IOException {
        systemUserService.register(systemUser.getFio(),systemUser.getUsername(),systemUser.getPassword1(), systemUser.getPassword2());
        return new ResponseEntity<>(HttpStatus.OK);

        // here we should validate email, name surname starts with upper case
    }

    @PostMapping("/login")
    public ResponseEntity<HttpHeaders> login(@RequestBody LoginSystemUser loginUser){
        authenticateUser(loginUser.getUsername(),loginUser.getPassword());
        SystemUser systemUser =systemUserService.findUserByUsername(loginUser.getUsername());
        SystemUserPrincipal systemUserPrincipal = new SystemUserPrincipal(systemUser);
        HttpHeaders jwtHeader = getJwtHeader(systemUserPrincipal);
        return new ResponseEntity<>(jwtHeader, HttpStatus.OK);
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
