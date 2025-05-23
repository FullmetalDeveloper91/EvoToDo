package ru.fmd.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "UserDetails")
public class User{
    @Id @GeneratedValue
    private int id;
    @Column(unique = true)
    @Length(min = 3, max = 50, message = "Length must be between 3 and 50")
    @NotBlank
    private String login;
    @Length(min = 4, max = 100, message = "Length must be between 4 and 100")
    @NotBlank
    private String password;
    @Length(min = 5, max = 100, message = "Length must be between 5 and 100")
    @NotBlank
    private String fio;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String fio, Role role) {
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
