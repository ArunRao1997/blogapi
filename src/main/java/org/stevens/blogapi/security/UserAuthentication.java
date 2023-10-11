package org.stevens.blogapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    /**
     * either JWT or UUID
     */
    private final String token;
    private String username;

    public UserAuthentication(String token) {
        this.token = token;
    }

    public void setUser(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return username != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (!isAuthenticated) {
            this.username = null;
        }
    }

    @Override
    public String getName() {
        //TO DO return username
        return null;
    }
}
