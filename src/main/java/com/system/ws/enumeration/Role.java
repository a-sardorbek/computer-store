package com.system.ws.enumeration;

import com.system.ws.constant.Authority;

public enum Role {
    ROLE_CUSTOMER(Authority.CUSTOMER_AUTHORITIES),
    ROLE_SYSTEM_USER(Authority.SYSTEM_USER_AUTHORITIES),
    ROLE_SYSTEM_ADMIN(Authority.SYSTEM_ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities){
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }

}
