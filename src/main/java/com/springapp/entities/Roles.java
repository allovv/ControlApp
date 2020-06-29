package com.springapp.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    USER, ADMIN;

    Roles() {}

    @Override
    public String getAuthority() {
        return name();
    }
}
