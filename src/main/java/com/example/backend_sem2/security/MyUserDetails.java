package com.example.backend_sem2.security;

import com.example.backend_sem2.entity.Authority;
import com.example.backend_sem2.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> authoritySet = user.getAuthoritySet();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Authority authority: authoritySet){
            System.out.println("***");
            System.out.println(authority.getAuthorityName());
            authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
