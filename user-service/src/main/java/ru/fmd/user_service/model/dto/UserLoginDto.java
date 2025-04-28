package ru.fmd.user_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import ru.fmd.user_service.model.User;

public class UserLoginDto {
    @Length(min = 3, max = 50, message = "Length must be between 3 and 50")
    @NotBlank
    private String login;
    @Length(min = 4, max = 20, message = "Length must be between 4 and 20")
    @NotBlank
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String login, String password) {
        this.login = login;
        this.password = password;
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

    public User toUser(){
        var user = new User();
        user.setLogin(this.login);
        user.setPassword(this.password);
        return user;
    }
}