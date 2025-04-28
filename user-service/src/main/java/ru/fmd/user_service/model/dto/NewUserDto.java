package ru.fmd.user_service.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import ru.fmd.user_service.model.Role;

public class NewUserDto {
    @Length(min = 3, max = 50, message = "Length must be between 3 and 50")
    @NotBlank
    private String login;
    @Length(min = 4, max = 20, message = "Length must be between 4 and 20")
    @NotBlank
    private String password;
    @Length(min = 5, max = 100, message = "Length must be between 5 and 100")
    @NotBlank
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