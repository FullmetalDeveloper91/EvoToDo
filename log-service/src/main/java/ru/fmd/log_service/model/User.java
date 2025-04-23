package ru.fmd.log_service.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    private final String AUTH_PREFIX = "ROLE_";

    private String login;
    private String password;
    private String fio;
    private Role role;

    public User() {
    }

    public User(String login, String password, String fio, Role role) {
        this.login = login;
        this.fio = fio;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role==null? List.of(): List.of((GrantedAuthority) () -> AUTH_PREFIX+role.name());
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
