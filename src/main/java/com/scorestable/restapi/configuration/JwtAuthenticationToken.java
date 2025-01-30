package com.scorestable.restapi.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;

    public JwtAuthenticationToken(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null; // Le JWT n'est pas un "credential" ici, c'est juste un jeton.
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}