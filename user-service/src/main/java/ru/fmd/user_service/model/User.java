package ru.fmd.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


//TODO Добавить валидацию
@Entity
@Table(name = "UserDetails")
public class User{
    @Id @GeneratedValue
    private int id;
    @Column(unique = true)
    @Min(value = 3, message = "Must be longer by 3")
    @Max(value = 10, message = "Must be shorter by 10")
    private String login;
    @Min(value = 4,message = "Must be longer by 4")
    @Max(value = 16, message = "Must be shorter by 16")
    private String password;
    @Min(value = 6,message = "Must be longer by 6")
    @Max(value = 100, message = "Must be shorter by 100")
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
