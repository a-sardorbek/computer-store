package com.system.ws.domain;

import com.system.ws.domain.entity.SystemUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class SystemUserPrincipal implements UserDetails {

    private SystemUser systemUser;

    public SystemUserPrincipal(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


//        List<SimpleGrantedAuthority> list = new ArrayList<>();
//        for (String s : this.user.getAuthorities()) {
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(s);
//            list.add(simpleGrantedAuthority);
//        }
//        return list;


        // here I am using stream instead of for-loop
        List<SimpleGrantedAuthority> collectAuthorities = stream(this.systemUser.getAuthorities())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return collectAuthorities;
    }

    @Override
    public String getPassword() {
        return systemUser.getPassword();
    }

    @Override
    public String getUsername() {
        return systemUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return systemUser.isNotActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return systemUser.isActive();
    }
}
