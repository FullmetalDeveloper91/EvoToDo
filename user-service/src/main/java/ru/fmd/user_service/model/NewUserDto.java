package ru.fmd.user_service.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class NewUserDto {
    @Min(value = 3, message = "Must be longer by 3")
    @Max(value = 10, message = "Must be shorter by 10")
    private String login;
    @Min(value = 4,message = "Must be longer by 4")
    @Max(value = 16, message = "Must be shorter by 16")
    private String password;
    @Min(value = 6,message = "Must be longer by 6")
    @Max(value = 30, message = "Must be shorter by 30")
    private String fio;
    private Role role;

    public NewUserDto() {
    }

    public NewUserDto(String login, String password, String fio, Role role) {
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}