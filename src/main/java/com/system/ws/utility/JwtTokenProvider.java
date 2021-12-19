package com.system.ws.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.system.ws.constant.SecurityConstant;
import com.system.ws.domain.SystemUserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        //  avoid the secret key available in the JVM
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

   public String generateJwtToken(SystemUserPrincipal systemUserPrincipal){
      String[] claims = getClaimsFromSystemUser(systemUserPrincipal);
      return JWT.create()
              .withIssuer(SecurityConstant.PRODUCT_STORE)
              .withAudience(SecurityConstant.PRODUCT_STORE_ADMINISTRATION)
              .withIssuedAt(new Date())
              .withSubject(systemUserPrincipal.getUsername())
              .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
              .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
              .sign(Algorithm.HMAC512(secret.getBytes()));
   }


    private String[] getClaimsFromSystemUser(SystemUserPrincipal systemUserPrincipal){
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : systemUserPrincipal.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);

    }



}
