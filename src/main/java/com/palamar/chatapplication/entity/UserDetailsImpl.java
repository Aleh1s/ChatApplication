package com.palamar.chatapplication.entity;

import com.palamar.chatapplication.entity.user.UserRole;
import com.palamar.chatapplication.entity.user.UserStatus;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;

@Builder
public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetails {

    private String email;
    private String password;
    private UserStatus userStatus;
    private UserRole userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return userStatus.equals(ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return userStatus.equals(ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userStatus.equals(ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return userStatus.equals(ACTIVE);
    }
}
