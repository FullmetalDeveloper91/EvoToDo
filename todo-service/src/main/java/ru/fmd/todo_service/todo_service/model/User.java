package ru.fmd.todo_service.todo_service.model;

public class User {
    private String login;
    private String password;
    private String fio;
    private Role role;

    public User() {
    }

    public User(String login, String password,String fio, Role role) {
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

    public String getPassword() {
        return password;
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
