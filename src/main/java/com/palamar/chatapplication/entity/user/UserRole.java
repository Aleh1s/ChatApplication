package com.palamar.chatapplication.entity.user;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static com.palamar.chatapplication.entity.user.UserPermission.*;
import static java.util.stream.Collectors.toSet;

public enum UserRole {

    USER(Sets.newHashSet(SEND_MESSAGE, READ_MESSAGE, CREATE_CHAT, DELETE_CHAT)),
    ADMIN(Sets.newHashSet(BLOCK_USER, READ_USER, WRITE_USER));

    private final Set<UserPermission> userPermissions;

    UserRole(Set<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public Set<UserPermission> getPermissions () {
        return userPermissions;
    }

    public Set<GrantedAuthority> getAuthorities () {
        return getPermissions().stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission()))
                .collect(toSet());
    }
}
